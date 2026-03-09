package servlets_1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CyberSecView {

    private static volatile CyberSecView instance;
    private final CyberSecSys cyberSecSys = new CyberSecSys();

    // Listas principais
    private volatile List<String> approach;
    private volatile List<String> performanceEvaluation;
    private volatile List<String> component;
    private volatile List<String> systems; // corrigido de 'systens'
    private volatile List<String> osapiSystemFunctions;
    private volatile List<String> osapiSystemFunctions2;
    private volatile List<String> osapiSystemFunctions3;
    private volatile List<String> defensiveTechnique;
    private volatile List<String> defensiveTechnique2;
    private volatile List<String> defensiveTechnique4;
    private volatile List<String> defensiveTechnique5;
    private volatile List<String> defensiveTechnique7;
    private volatile List<String> attackEnterpriseMitigation;
    private volatile List<String> attackEnterpriseMitigation1;

    // Estado de carregamento
    private volatile boolean carregado = false;
    private volatile boolean erroCarregamento = false;
    private volatile boolean carregamentoEmBackground = false;
    private String mensagemErro = "";

    private long tempoCarregamento = 0L;
    private final Date dataCriacao = new Date();
    private volatile Date dataUltimoCarregamento;
    private int totalRequisicoes = 0;
    private final Map<String, Integer> estatisticasAcesso = new ConcurrentHashMap<>();

    private static final List<String> LISTA_VAZIA = Collections.emptyList();
    private static final String LOG_PREFIX = "[CyberSecView] ";

    @FunctionalInterface
    private interface SupplierList {
        List<String> get() throws Exception;
    }

    CyberSecView() {
        logInfo("🏗️ CyberSecView construída em " + dataCriacao);
        inicializarListasVazias();
    }

    public static CyberSecView getInstance() {
        if (instance == null) {
            synchronized (CyberSecView.class) {
                if (instance == null) {
                    instance = new CyberSecView();
                    instance.logInfo("✨ Nova instância de CyberSecView criada");
                }
            }
        }
        return instance;
    }

    // ================= Carregamento de dados =================
    public synchronized void carregarDados() {
        if (carregado) {
            logInfo("📦 Dados já carregados — reutilizando cache");
            return;
        }

        logInfo("🚀 Iniciando carregamento da CyberSecView...");
        long startTime = System.currentTimeMillis();
        dataUltimoCarregamento = new Date();

        try {
            approach = carregarListaSegura("Approach", cyberSecSys::Approach);
            performanceEvaluation = carregarListaSegura("PerformanceEvaluation", cyberSecSys::PerformanceEvaluation);
            component = carregarListaSegura("Component", cyberSecSys::Component);
            osapiSystemFunctions = carregarListaSegura("OSAPISystemFunctions", cyberSecSys::OSAPISystemFunctions);

            carregado = true;
            erroCarregamento = false;
            mensagemErro = "";
            tempoCarregamento = System.currentTimeMillis() - startTime;

            logInfo(String.format("🎉 Carregamento concluído com sucesso em %dms", tempoCarregamento));
            logInfo(String.format("📊 Totais: %d itens essenciais", getTotalItensEssenciais()));

            iniciarCarregamentoBackground();

        } catch (Exception e) {
            tratarErroCarregamento(e, startTime);
        }
    }

    private List<String> carregarListaSegura(String nome, SupplierList supplier) {
        try {
            List<String> lista = supplier.get();
            List<String> listaImutavel = (lista != null && !lista.isEmpty())
                    ? Collections.unmodifiableList(new ArrayList<>(lista))
                    : LISTA_VAZIA;
            logInfo(String.format("✅ %s carregado: %d itens", nome, listaImutavel.size()));
            return listaImutavel;
        } catch (Exception e) {
            logError("❌ Erro ao carregar " + nome, e);
            return LISTA_VAZIA;
        }
    }

    // ================= Carregamento em background =================
    private void iniciarCarregamentoBackground() {
        if (carregamentoEmBackground) return;

        carregamentoEmBackground = true;

        Thread loader = new Thread(() -> {
            Thread.currentThread().setName("CyberSecView-BackgroundLoader");
            logInfo("🔧 Thread de background iniciada");

            try {
                long bgStart = System.currentTimeMillis();

                // corrigido: systems
                systems = carregarListaSegura("Systems", cyberSecSys::Systens);

                osapiSystemFunctions2 = carregarListaSegura("OSAPISystemFunctions_2", cyberSecSys::OSAPISystemFunctions_2);
                osapiSystemFunctions3 = carregarListaSegura("OSAPISystemFunctions_3", cyberSecSys::OSAPISystemFunctions_3);
                defensiveTechnique = carregarListaSegura("DefensiveTechnique", cyberSecSys::DefensiveTechnique);
                defensiveTechnique2 = carregarListaSegura("DefensiveTechnique_2", cyberSecSys::DefensiveTechnique_2);
                defensiveTechnique4 = carregarListaSegura("DefensiveTechnique_4", cyberSecSys::DefensiveTechnique_4);
                defensiveTechnique5 = carregarListaSegura("DefensiveTechnique_5", cyberSecSys::DefensiveTechnique_5);
                defensiveTechnique7 = carregarListaSegura("DefensiveTechnique_7", cyberSecSys::DefensiveTechnique_7);
                attackEnterpriseMitigation = carregarListaSegura("AttackEnterpriseMitigation", cyberSecSys::AttackEnterpriseMitigation);
                attackEnterpriseMitigation1 = carregarListaSegura("AttackEnterpriseMitigation_1", cyberSecSys::AttackEnterpriseMitigation_1);

                long elapsed = System.currentTimeMillis() - bgStart;
                logInfo(String.format("🎊 Carregamento em background concluído (%dms) - Total geral: %d itens",
                        elapsed, getTotalItens()));

            } catch (Exception e) {
                logError("⚠️ Erro no carregamento em background", e);
            } finally {
                carregamentoEmBackground = false;
            }
        });

        loader.setDaemon(true);
        loader.start();
    }

    private List<String> carregarSobDemanda(String nome, SupplierList supplier) {
        synchronized (this) {
            logInfo(String.format("🔄 Carregando %s sob demanda...", nome));
            try {
                List<String> lista = supplier.get();
                List<String> listaImutavel = (lista != null && !lista.isEmpty())
                        ? Collections.unmodifiableList(new ArrayList<>(lista))
                        : LISTA_VAZIA;
                logInfo(String.format("✅ %s carregado sob demanda: %d itens", nome, listaImutavel.size()));
                return listaImutavel;
            } catch (Exception e) {
                logError("❌ Erro ao carregar " + nome + " sob demanda", e);
                return LISTA_VAZIA;
            }
        }
    }

    private void tratarErroCarregamento(Exception e, long startTime) {
        tempoCarregamento = System.currentTimeMillis() - startTime;
        erroCarregamento = true;
        mensagemErro = e.getMessage() != null ? e.getMessage() : "Erro desconhecido";
        logError("💥 Erro crítico no carregamento (" + tempoCarregamento + "ms)", e);
        inicializarListasVazias();
    }

    private void inicializarListasVazias() {
        approach = LISTA_VAZIA;
        performanceEvaluation = LISTA_VAZIA;
        component = LISTA_VAZIA;
        systems = LISTA_VAZIA; // corrigido
        osapiSystemFunctions = LISTA_VAZIA;
        osapiSystemFunctions2 = LISTA_VAZIA;
        osapiSystemFunctions3 = LISTA_VAZIA;
        defensiveTechnique = LISTA_VAZIA;
        defensiveTechnique2 = LISTA_VAZIA;
        defensiveTechnique4 = LISTA_VAZIA;
        defensiveTechnique5 = LISTA_VAZIA;
        defensiveTechnique7 = LISTA_VAZIA;
        attackEnterpriseMitigation = LISTA_VAZIA;
        attackEnterpriseMitigation1 = LISTA_VAZIA;
    }

    // ================= Getters =================
    public List<String> getApproach() {
        registrarAcesso("approach");
        garantirDadosCarregados();
        return approach;
    }

    public List<String> getPerformanceEvaluation() {
        registrarAcesso("performanceEvaluation");
        garantirDadosCarregados();
        return performanceEvaluation;
    }

    public List<String> getComponent() {
        registrarAcesso("component");
        garantirDadosCarregados();
        return component;
    }

    public List<String> getSystems() { // corrigido
        registrarAcesso("systems");
        return obterListaSegura("systems", systems, cyberSecSys::Systens);
    }

    public List<String> getOSAPISystemFunctions() {
        registrarAcesso("osapiSystemFunctions");
        garantirDadosCarregados();
        return osapiSystemFunctions;
    }

    public List<String> getOSAPISystemFunctions2() {
        registrarAcesso("osapiSystemFunctions2");
        return obterListaSegura("OSAPISystemFunctions_2", osapiSystemFunctions2, cyberSecSys::OSAPISystemFunctions_2);
    }

    public List<String> getOSAPISystemFunctions3() {
        registrarAcesso("osapiSystemFunctions3");
        return obterListaSegura("OSAPISystemFunctions_3", osapiSystemFunctions3, cyberSecSys::OSAPISystemFunctions_3);
    }

    public List<String> getDefensiveTechnique() {
        registrarAcesso("defensiveTechnique");
        return obterListaSegura("DefensiveTechnique", defensiveTechnique, cyberSecSys::DefensiveTechnique);
    }

    public List<String> getDefensiveTechnique2() {
        registrarAcesso("defensiveTechnique2");
        return obterListaSegura("DefensiveTechnique_2", defensiveTechnique2, cyberSecSys::DefensiveTechnique_2);
    }

    public List<String> getDefensiveTechnique4() {
        registrarAcesso("defensiveTechnique4");
        return obterListaSegura("DefensiveTechnique_4", defensiveTechnique4, cyberSecSys::DefensiveTechnique_4);
    }

    public List<String> getDefensiveTechnique5() {
        registrarAcesso("defensiveTechnique5");
        return obterListaSegura("DefensiveTechnique_5", defensiveTechnique5, cyberSecSys::DefensiveTechnique_5);
    }

    public List<String> getDefensiveTechnique7() {
        registrarAcesso("defensiveTechnique7");
        return obterListaSegura("DefensiveTechnique_7", defensiveTechnique7, cyberSecSys::DefensiveTechnique_7);
    }

    public List<String> getAttackEnterpriseMitigation() {
        registrarAcesso("attackEnterpriseMitigation");
        return obterListaSegura("AttackEnterpriseMitigation", attackEnterpriseMitigation, cyberSecSys::AttackEnterpriseMitigation);
    }

    public List<String> getAttackEnterpriseMitigation1() {
        registrarAcesso("attackEnterpriseMitigation1");
        return obterListaSegura("AttackEnterpriseMitigation_1", attackEnterpriseMitigation1, cyberSecSys::AttackEnterpriseMitigation_1);
    }

    // ================= Métodos auxiliares =================
    private List<String> obterListaSegura(final String nome, List<String> lista, final SupplierList supplier) {
        garantirDadosCarregados();

        if (lista != LISTA_VAZIA && lista != null) {
            return lista;
        }

        synchronized (this) {
            if (lista == LISTA_VAZIA || lista == null) {
                final List<String> novaLista = carregarSobDemanda(nome, supplier);

                switch (nome) {
                    case "systems": return systems = novaLista;
                    case "OSAPISystemFunctions_2": return osapiSystemFunctions2 = novaLista;
                    case "OSAPISystemFunctions_3": return osapiSystemFunctions3 = novaLista;
                    case "DefensiveTechnique": return defensiveTechnique = novaLista;
                    case "DefensiveTechnique_2": return defensiveTechnique2 = novaLista;
                    case "DefensiveTechnique_4": return defensiveTechnique4 = novaLista;
                    case "DefensiveTechnique_5": return defensiveTechnique5 = novaLista;
                    case "DefensiveTechnique_7": return defensiveTechnique7 = novaLista;
                    case "AttackEnterpriseMitigation": return attackEnterpriseMitigation = novaLista;
                    case "AttackEnterpriseMitigation_1": return attackEnterpriseMitigation1 = novaLista;
                    default: return novaLista;
                }
            }
            return lista;
        }
    }

    private void garantirDadosCarregados() {
        if (!carregado && !erroCarregamento) {
            carregarDados();
        }
    }

    // ================= Informações gerais =================
    public boolean isCarregado() { return carregado; }
    public boolean isErroCarregamento() { return erroCarregamento; }
    public String getMensagemErro() { return mensagemErro; }
    public long getTempoCarregamento() { return tempoCarregamento; }
    public Date getDataCriacao() { return new Date(dataCriacao.getTime()); }
    public Date getDataUltimoCarregamento() {
        return dataUltimoCarregamento != null ? new Date(dataUltimoCarregamento.getTime()) : null;
    }

    public int getTotalItensEssenciais() {
        return (approach != LISTA_VAZIA ? approach.size() : 0) +
               (performanceEvaluation != LISTA_VAZIA ? performanceEvaluation.size() : 0) +
               (component != LISTA_VAZIA ? component.size() : 0) +
               (osapiSystemFunctions != LISTA_VAZIA ? osapiSystemFunctions.size() : 0);
    }

    public int getTotalItens() {
        int total = 0;
        List<List<String>> listas = Arrays.asList(
                approach, performanceEvaluation, component, systems,
                osapiSystemFunctions, osapiSystemFunctions2, osapiSystemFunctions3,
                defensiveTechnique, defensiveTechnique2, defensiveTechnique4,
                defensiveTechnique5, defensiveTechnique7,
                attackEnterpriseMitigation, attackEnterpriseMitigation1
        );

        for (List<String> lista : listas) {
            if (lista != null && lista != LISTA_VAZIA) {
                total += lista.size();
            }
        }
        return total;
    }

    public String getStatus() {
        if (erroCarregamento) return String.format("❌ ERRO: %s (%dms)", mensagemErro, tempoCarregamento);
        if (!carregado) return "⏳ NÃO CARREGADO";
        return String.format("✅ CARREGADO - %d itens em %dms", getTotalItens(), tempoCarregamento);
    }

    private void registrarAcesso(String metodo) {
        totalRequisicoes++;
        estatisticasAcesso.put(metodo, estatisticasAcesso.getOrDefault(metodo, 0) + 1);
        if (totalRequisicoes % 10 == 0) {
            logInfo(String.format("📈 Acesso: total=%d, método=%s", totalRequisicoes, metodo));
        }
    }

    public Map<String, Integer> getEstatisticasAcesso() {
        return new ConcurrentHashMap<>(estatisticasAcesso);
    }

    public int getTotalRequisicoes() { return totalRequisicoes; }

    private void logInfo(String msg) {
        System.out.println(LOG_PREFIX + msg);
    }

    private void logError(String msg, Exception e) {
        System.err.println(LOG_PREFIX + msg + " → " + e.getMessage());
        if (e != null) e.printStackTrace(System.err);
    }

    public synchronized void recarregarDados() {
        logInfo("🔄 Recarregando dados...");
        carregado = false;
        erroCarregamento = false;
        mensagemErro = "";
        inicializarListasVazias();
        carregarDados();
    }

    public boolean isCompletamenteCarregado() {
        return carregado &&
               systems != LISTA_VAZIA &&
               osapiSystemFunctions2 != LISTA_VAZIA &&
               osapiSystemFunctions3 != LISTA_VAZIA &&
               defensiveTechnique7 != LISTA_VAZIA &&
               attackEnterpriseMitigation1 != LISTA_VAZIA;
    }

    public boolean isCarregamentoEmBackground() { return carregamentoEmBackground; }

    public String getResumo() {
        return String.format(
                "CyberSecView{carregado=%s, itens=%d, requisicoes=%d, criada=%s, bg=%s}",
                carregado,
                getTotalItens(),
                totalRequisicoes,
                dataCriacao,
                carregamentoEmBackground ? "ativo" : "inativo"
        );
    }

    @Override
    public String toString() {
        return getResumo();
    }
}