package servlets_1;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.ontology.OntResource;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.jena.vocabulary.DC_11;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDFS;
import org.apache.jena.vocabulary.SKOS;

public class CyberSecSys_2 {
    
    private static final String NS = "http://www.ppgi.uniriotec.br/CyberSecSys_1.0#";
    private static final String D3FEND_NS = "http://d3fend.mitre.org/ontologies/d3fend.owl#";
    private static final String DEFAULT_FILENAME = "CyberSecSys.ttl";
    
    private OntModel carregarOntologia(String fileName) {
        OntModel ontology = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        try {
            ClassLoader classLoader = this.getClass().getClassLoader();
            URL resource = classLoader.getResource(fileName);
            if (resource == null) {
                System.err.println("Arquivo não encontrado: " + fileName);
                System.err.println("Diretórios pesquisados: " + System.getProperty("java.class.path"));
                return ontology;
            }
            Path path = Paths.get(resource.toURI());
            System.out.println("Carregando ontologia de: " + path.toString());
            ontology.read(path.toUri().toString(), "Turtle");
            System.out.println("Ontologia carregada com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao carregar ontologia: " + e.getMessage());
            e.printStackTrace();
        }
        return ontology;
    }
    
    private List<String> listarInstanciasDaClasse(String nomeClasse, String nomeArquivo, boolean formatar) {
        OntModel ontology = carregarOntologia(nomeArquivo);
        List<String> resultado = new ArrayList<>();
        
        System.out.println("Buscando classe: " + NS + nomeClasse);
        OntClass classe = ontology.getOntClass(NS + nomeClasse);
        if (classe != null) {
            System.out.println("Classe encontrada: " + classe.getLocalName());
            ExtendedIterator<? extends OntResource> instances = classe.listInstances();
            try {
                int count = 0;
                while (instances.hasNext()) {
                    OntResource instance = instances.next();
                    String localName = instance.getLocalName();
                    if (localName != null) {
                        if (formatar) {
                            resultado.add(localName.replace("_", " "));
                        } else {
                            resultado.add(localName);
                        }
                        count++;
                    }
                }
                System.out.println("Encontradas " + count + " instâncias para a classe " + nomeClasse);
            } finally {
                instances.close();
            }
            Collections.sort(resultado);
        } else {
            System.out.println("Classe " + nomeClasse + " não encontrada na ontologia");
            // Listar todas as classes disponíveis para debug
            listarTodasClasses(ontology);
        }
        
        return resultado;
    }
    
    private void listarTodasClasses(OntModel ontology) {
        System.out.println("Classes disponíveis na ontologia:");
        ExtendedIterator<OntClass> classes = ontology.listClasses();
        try {
            while (classes.hasNext()) {
                OntClass c = classes.next();
                if (c.getURI() != null) {
                    System.out.println("  - " + c.getURI());
                }
            }
        } finally {
            classes.close();
        }
    }
    
    public List<String> Approach() {
        return listarInstanciasDaClasse("Approach", DEFAULT_FILENAME, true);
    }
    
    public List<String> PerformanceEvaluation() {
        return listarInstanciasDaClasse("PerformanceEvaluation", DEFAULT_FILENAME, true);
    }
    
    public List<String> Component() {
        return listarInstanciasDaClasse("Component", DEFAULT_FILENAME, true);
    }
    
    public List<String> Systems() {
        String[] classesSistema = {
            "Collaborative_Systems_Component",
            "Cyber-PhysicalSystem_Component", 
            "IntelligentSystem_Component",
            "System-Of-Systems_Component"
        };
        
        OntModel ontology = carregarOntologia("CyberSecSys_3.ttl");
        LinkedHashSet<String> uniqueList = new LinkedHashSet<>();
        
        for (String nomeClasse : classesSistema) {
            System.out.println("Buscando classe: " + NS + nomeClasse);
            OntClass classe = ontology.getOntClass(NS + nomeClasse);
            if (classe != null) {
                System.out.println("Classe encontrada: " + classe.getLocalName());
                ExtendedIterator<? extends OntResource> instances = classe.listInstances();
                try {
                    int count = 0;
                    while (instances.hasNext()) {
                        OntResource instance = instances.next();
                        String localName = instance.getLocalName();
                        if (localName != null) {
                            uniqueList.add(localName);
                            count++;
                        }
                    }
                    System.out.println("Encontradas " + count + " instâncias para " + nomeClasse);
                } finally {
                    instances.close();
                }
            } else {
                System.out.println("Classe " + nomeClasse + " não encontrada");
            }
        }
        
        List<String> resultado = new ArrayList<>(uniqueList);
        Collections.sort(resultado);
        
        // Processar nomes
        resultado = resultado.stream()
            .map(item -> item.replace("_Component", "")
                             .replace("Component_", "")
                             .replace("_", " ")
                             .replaceAll("\\bComponent\\b", "")
                             .trim()
                             .replaceAll("\\s+", " ")
                             .replaceAll("(?<!-|^)([A-Z])", " $1"))
            .filter(nome -> !nome.isEmpty())
            .collect(Collectors.toList());
        
        System.out.println("Systems resultado: " + resultado);
        return resultado;
    }
    
    public List<String> OSAPISystemFunctions() {
        return listarOSAPIFunctions(DEFAULT_FILENAME, false);
    }
    
    public List<String> OSAPISystemFunctions_1() {
        return listarOSAPIFunctions(DEFAULT_FILENAME, true);
    }
    
    public List<String> OSAPISystemFunctions_2() {
        return listarOSAPIFunctionsComDescricao(DEFAULT_FILENAME);
    }
    
    public List<String> OSAPISystemFunctions_3() {
        return listarOSAPIFunctionsComDescricaoAvancada(DEFAULT_FILENAME);
    }
    
    public List<String> OSAPISystemFunctions_9() {
        return listarOSAPIFunctions(DEFAULT_FILENAME, true);
    }
    
    private List<String> listarOSAPIFunctions(String nomeArquivo, boolean formatarNome) {
        OntModel ontology = carregarOntologia(nomeArquivo);
        LinkedHashSet<String> uniqueList = new LinkedHashSet<>();
        
        System.out.println("Buscando classe OSAPISystemFunction...");
        ExtendedIterator<OntClass> classIterator = ontology.listClasses();
        try {
            while (classIterator.hasNext()) {
                OntClass classe = classIterator.next();
                String localName = classe.getLocalName();
                if (localName != null && localName.equals("OSAPISystemFunction")) {
                    System.out.println("Classe OSAPISystemFunction encontrada!");
                    ExtendedIterator<? extends OntResource> instances = classe.listInstances();
                    try {
                        int count = 0;
                        while (instances.hasNext()) {
                            OntResource instance = instances.next();
                            String nomeOriginal = instance.getLocalName();
                            if (nomeOriginal != null) {
                                uniqueList.add(nomeOriginal);
                                count++;
                            }
                        }
                        System.out.println("Encontradas " + count + " instâncias de OSAPISystemFunction");
                    } finally {
                        instances.close();
                    }
                }
            }
        } finally {
            classIterator.close();
        }
        
        if (uniqueList.isEmpty()) {
            System.out.println("Nenhuma instância encontrada. Tentando busca alternativa...");
            return buscarOSAPIFunctionsAlternativo(ontology);
        }
        
        List<String> resultado = new ArrayList<>(uniqueList);
        Collections.sort(resultado);
        
        if (formatarNome) {
            resultado = resultado.stream()
                .map(this::formatarNomeOSAPI)
                .collect(Collectors.toList());
        }
        
        System.out.println("OSAPI Functions encontradas: " + resultado.size());
        return resultado;
    }
    
    private List<String> buscarOSAPIFunctionsAlternativo(OntModel ontology) {
        List<String> resultado = new ArrayList<>();
        
        // Tentar encontrar instâncias diretamente
        ExtendedIterator<Individual> individuals = ontology.listIndividuals();
        try {
            int count = 0;
            while (individuals.hasNext()) {
                Individual ind = individuals.next();
                String localName = ind.getLocalName();
                if (localName != null && localName.toLowerCase().contains("osapi")) {
                    resultado.add(localName);
                    count++;
                }
            }
            System.out.println("Busca alternativa encontrou " + count + " indivíduos relacionados a OSAPI");
        } finally {
            individuals.close();
        }
        
        Collections.sort(resultado);
        return resultado;
    }
    
    private List<String> listarOSAPIFunctionsComDescricao(String nomeArquivo) {
        OntModel ontology = carregarOntologia(nomeArquivo);
        List<String> resultado = new ArrayList<>();
        LinkedHashSet<String> uniqueSet = new LinkedHashSet<>();
        
        ExtendedIterator<OntClass> classIterator = ontology.listClasses();
        try {
            while (classIterator.hasNext()) {
                OntClass classe = classIterator.next();
                if ("OSAPISystemFunction".equals(classe.getLocalName())) {
                    ExtendedIterator<? extends OntResource> instances = classe.listInstances();
                    try {
                        while (instances.hasNext()) {
                            OntResource instance = instances.next();
                            String nomeOriginal = instance.getLocalName();
                            if (nomeOriginal == null || !uniqueSet.add(nomeOriginal)) continue;
                            
                            String descricao = "";
                            Statement stmt = instance.getProperty(ontology.getProperty(NS + "definition"));
                            if (stmt == null) {
                                stmt = instance.getProperty(RDFS.comment);
                            }
                            
                            if (stmt != null && stmt.getObject().isLiteral()) {
                                descricao = stmt.getString();
                            }
                            
                            String nomeFormatado = formatarNomeOSAPI(nomeOriginal);
                            resultado.add(nomeFormatado + (descricao.isEmpty() ? "" : " — " + descricao));
                        }
                    } finally {
                        instances.close();
                    }
                }
            }
        } finally {
            classIterator.close();
        }
        
        Collections.sort(resultado);
        return resultado;
    }
    
    private List<String> listarOSAPIFunctionsComDescricaoAvancada(String nomeArquivo) {
        OntModel ontology = carregarOntologia(nomeArquivo);
        List<String> resultado = new ArrayList<>();
        
        ExtendedIterator<OntClass> classIterator = ontology.listClasses();
        try {
            while (classIterator.hasNext()) {
                OntClass classe = classIterator.next();
                if ("OSAPISystemFunction".equals(classe.getLocalName())) {
                    ExtendedIterator<? extends OntResource> instances = classe.listInstances();
                    try {
                        while (instances.hasNext()) {
                            OntResource instance = instances.next();
                            String nomeOriginal = instance.getLocalName();
                            if (nomeOriginal == null) continue;
                            
                            String descricao = extrairDescricao(instance, ontology, NS);
                            if (descricao.isEmpty()) {
                                ExtendedIterator<Resource> tipos = instance.listRDFTypes(true);
                                try {
                                    while (tipos.hasNext()) {
                                        Resource tipo = tipos.next();
                                        descricao = extrairDescricao(tipo.as(OntResource.class), ontology, NS);
                                        if (!descricao.isEmpty()) break;
                                    }
                                } finally {
                                    tipos.close();
                                }
                            }
                            
                            String nomeFormatado = formatarNomeOSAPI(nomeOriginal);
                            descricao = descricao.isEmpty() ? "No description available." : descricao;
                            resultado.add(nomeFormatado + ": " + descricao);
                        }
                    } finally {
                        instances.close();
                    }
                }
            }
        } finally {
            classIterator.close();
        }
        
        Collections.sort(resultado);
        return resultado;
    }
    
    private String formatarNomeOSAPI(String nomeOriginal) {
        if (nomeOriginal == null) return "";
        
        // Primeiro, substituir padrões comuns
        String processado = nomeOriginal;
        
        // Adicionar espaços antes de letras maiúsculas (exceto no início)
        processado = processado.replaceAll("([a-z])([A-Z])", "$1 $2");
        processado = processado.replaceAll("([A-Z])([A-Z][a-z])", "$1 $2");
        
        // Tratar casos especiais como OSAPI
        processado = processado.replace("OSAPI", "OS API");
        processado = processado.replace("OSApi", "OS API");
        
        // Substituir underscores por espaços
        processado = processado.replace("_", " ");
        
        // Remover espaços extras
        processado = processado.replaceAll("\\s+", " ").trim();
        
        return processado;
    }
    
    private String extrairDescricao(OntResource recurso, OntModel modelo, String ns) {
        Property[] props = {
            modelo.getProperty(ns + "definition"),
            modelo.getProperty(ns + "descricao"),
            modelo.getProperty(ns + "description"),
            SKOS.definition,
            RDFS.comment,
            OWL.versionInfo,
            DC_11.description
        };
        
        for (Property p : props) {
            if (p == null) continue;
            Statement stmt = recurso.getProperty(p);
            if (stmt != null && stmt.getObject().isLiteral()) {
                String descricao = stmt.getLiteral().getString().trim();
                if (!descricao.isEmpty()) {
                    return descricao;
                }
            }
        }
        return "";
    }
    
    public List<String> DefensiveTechnique() {
        return listarDefensiveTechnique("CyberSecSys_2.ttl", false);
    }
    
    public List<String> DefensiveTechnique_1() {
        return listarDefensiveTechnique("CyberSecSys_2.ttl", true);
    }
    
    public List<String> DefensiveTechnique_2() {
        return listarDefensiveTechniqueComDescricao("CyberSecSys_2.ttl");
    }
    
    public List<String> DefensiveTechnique_3() {
        return listarDefensiveTechniqueComDescricaoEspecifica("CyberSecSys_2.ttl");
    }
    
    public List<String> DefensiveTechnique_4() {
        return listarDefensiveTechniqueFiltrada("CyberSecSys_4.ttl", false);
    }
    
    public List<String> DefensiveTechnique_5() {
        return listarDefensiveTechniqueFiltrada("CyberSecSys_4.ttl", true);
    }
    
    public List<String> DefensiveTechnique_6() {
        return listarDefensiveTechniqueAvancada("CyberSecSys_4.ttl");
    }
    
    public List<String> DefensiveTechnique_41() {
        return listarDefensiveTechniqueComRemocaoNome("CyberSecSys_4.ttl");
    }
    
    public List<String> DefensiveTechnique_51() {
        return listarDefensiveTechniqueComRemocaoNome("CyberSecSys_4.ttl");
    }
    
    public List<String> DefensiveTechnique_7() {
        List<String> resultado = listarDefensiveTechniqueComRemocaoNome("CyberSecSys_4.ttl");
        resultado = resultado.stream().distinct().sorted().collect(Collectors.toList());
        System.out.println("Instâncias de DefensiveTechnique com definição filtrada: " + resultado);
        return resultado;
    }
    
    private List<String> listarDefensiveTechnique(String nomeArquivo, boolean formatarCapitalizado) {
        OntModel ontology = carregarOntologia(nomeArquivo);
        List<String> resultado = new ArrayList<>();
        
        System.out.println("Buscando classe DefensiveTechnique em: " + D3FEND_NS);
        OntClass defensiveTechniqueClass = ontology.getOntClass(D3FEND_NS + "DefensiveTechnique");
        if (defensiveTechniqueClass != null) {
            System.out.println("Classe DefensiveTechnique encontrada!");
            ExtendedIterator<? extends OntResource> instances = defensiveTechniqueClass.listInstances();
            try {
                int count = 0;
                while (instances.hasNext()) {
                    OntResource instance = instances.next();
                    String uri = instance.getURI();
                    if (uri != null && uri.startsWith(D3FEND_NS)) {
                        String localName = uri.substring(D3FEND_NS.length());
                        if (formatarCapitalizado) {
                            resultado.add(formatarNomeCapitalizado(localName));
                        } else {
                            resultado.add(localName.replace("_", " "));
                        }
                        count++;
                    }
                }
                System.out.println("Encontradas " + count + " instâncias de DefensiveTechnique");
            } finally {
                instances.close();
            }
        } else {
            System.out.println("Classe DefensiveTechnique não encontrada");
            listarTodasClasses(ontology);
        }
        
        Collections.sort(resultado);
        return resultado;
    }
    
    private List<String> listarDefensiveTechniqueComDescricao(String nomeArquivo) {
        OntModel ontology = carregarOntologia(nomeArquivo);
        List<String> resultado = new ArrayList<>();
        
        OntClass defensiveTechniqueClass = ontology.getOntClass(D3FEND_NS + "DefensiveTechnique");
        if (defensiveTechniqueClass != null) {
            ExtendedIterator<? extends OntResource> instances = defensiveTechniqueClass.listInstances();
            try {
                while (instances.hasNext()) {
                    OntResource instance = instances.next();
                    String uri = instance.getURI();
                    if (uri != null && uri.startsWith(D3FEND_NS)) {
                        String localName = uri.substring(D3FEND_NS.length());
                        String nomeFormatado = formatarNomeCapitalizado(localName);
                        String descricao = extrairDescricao(instance, ontology, D3FEND_NS);
                        
                        if (descricao.isEmpty()) {
                            ExtendedIterator<Resource> tipos = instance.listRDFTypes(true);
                            try {
                                while (tipos.hasNext()) {
                                    Resource tipo = tipos.next();
                                    descricao = extrairDescricao(tipo.as(OntResource.class), ontology, D3FEND_NS);
                                    if (!descricao.isEmpty()) break;
                                }
                            } finally {
                                tipos.close();
                            }
                        }
                        
                        resultado.add(nomeFormatado + " — " + (descricao.isEmpty() ? "" : descricao));
                    }
                }
            } finally {
                instances.close();
            }
        }
        
        Collections.sort(resultado);
        return resultado;
    }
    
    private List<String> listarDefensiveTechniqueComDescricaoEspecifica(String nomeArquivo) {
        OntModel ontology = carregarOntologia(nomeArquivo);
        List<String> resultado = new ArrayList<>();
        
        Property definitionProp = ontology.getProperty(D3FEND_NS + "definition");
        OntClass defensiveTechniqueClass = ontology.getOntClass(D3FEND_NS + "DefensiveTechnique");
        
        if (defensiveTechniqueClass != null && definitionProp != null) {
            ExtendedIterator<? extends OntResource> instances = defensiveTechniqueClass.listInstances();
            try {
                while (instances.hasNext()) {
                    OntResource instance = instances.next();
                    String uri = instance.getURI();
                    if (uri != null && uri.startsWith(D3FEND_NS)) {
                        String localName = uri.substring(D3FEND_NS.length());
                        String nomeFormatado = formatarNomeCapitalizado(localName);
                        String descricao = "";
                        
                        Statement stmt = instance.getProperty(definitionProp);
                        if (stmt != null && stmt.getObject().isLiteral()) {
                            descricao = stmt.getLiteral().getString().trim();
                        }
                        
                        resultado.add(nomeFormatado + " — " + (descricao.isEmpty() ? "(No description available.)" : descricao));
                    }
                }
            } finally {
                instances.close();
            }
        }
        
        Collections.sort(resultado);
        return resultado;
    }
    
    private List<String> listarDefensiveTechniqueFiltrada(String nomeArquivo, boolean removerNome) {
        OntModel ontology = carregarOntologia(nomeArquivo);
        List<String> resultado = new ArrayList<>();
        
        Property definitionProp = ontology.getProperty(D3FEND_NS + "definition");
        OntClass defensiveTechniqueClass = ontology.getOntClass(D3FEND_NS + "DefensiveTechnique");
        
        if (defensiveTechniqueClass != null && definitionProp != null) {
            ExtendedIterator<? extends OntResource> instances = defensiveTechniqueClass.listInstances(true);
            try {
                while (instances.hasNext()) {
                    OntResource instance = instances.next();
                    if (!instance.isURIResource()) continue;
                    
                    String uri = instance.getURI();
                    if (uri == null || !uri.startsWith(D3FEND_NS)) continue;
                    
                    String localName = uri.substring(D3FEND_NS.length());
                    if (!instance.hasRDFType(defensiveTechniqueClass)) continue;
                    
                    Statement stmt = instance.getProperty(definitionProp);
                    if (stmt == null || !stmt.getObject().isLiteral()) continue;
                    
                    String descricao = stmt.getLiteral().getString().trim();
                    if (descricao.isEmpty() || descricao.startsWith("Platforms includes") || descricao.length() < 10) continue;
                    
                    String nomeFormatado = formatarNomeCapitalizado(localName);
                    if (removerNome) {
                        descricao = removerNomeDaDescricao(nomeFormatado, descricao);
                    }
                    
                    resultado.add(nomeFormatado + ": " + descricao);
                }
            } finally {
                instances.close();
            }
        }
        
        return resultado.stream().distinct().sorted().collect(Collectors.toList());
    }
    
    private List<String> listarDefensiveTechniqueAvancada(String nomeArquivo) {
        OntModel ontology = carregarOntologia(nomeArquivo);
        List<String> resultado = new ArrayList<>();
        
        Property definitionProp = ontology.getProperty(D3FEND_NS + "definition");
        OntClass defensiveTechniqueClass = ontology.getOntClass(D3FEND_NS + "DefensiveTechnique");
        
        if (defensiveTechniqueClass != null && definitionProp != null) {
            ExtendedIterator<? extends OntResource> instances = defensiveTechniqueClass.listInstances(true);
            try {
                while (instances.hasNext()) {
                    OntResource instance = instances.next();
                    if (!instance.isURIResource()) continue;
                    
                    String uri = instance.getURI();
                    if (uri == null || !uri.startsWith(D3FEND_NS)) continue;
                    
                    String localName = uri.substring(D3FEND_NS.length());
                    if (!instance.hasRDFType(defensiveTechniqueClass)) continue;
                    
                    Statement stmt = instance.getProperty(definitionProp);
                    if (stmt == null || !stmt.getObject().isLiteral()) continue;
                    
                    String descricao = stmt.getLiteral().getString().trim();
                    if (descricao.isEmpty() || descricao.length() < 10) continue;
                    
                    String lowerDesc = descricao.toLowerCase();
                    if (lowerDesc.startsWith("platforms includes") ||
                        lowerDesc.startsWith("user behavior analytics") ||
                        lowerDesc.startsWith("system mapping encompasses") ||
                        lowerDesc.startsWith("big data platforms") ||
                        lowerDesc.contains("includes components such as:")) {
                        continue;
                    }
                    
                    String nomeFormatado = formatarNomeCapitalizado(localName);
                    descricao = removerNomeDaDescricao(nomeFormatado, descricao);
                    
                    if (descricao.contains(".")) {
                        descricao = descricao.substring(0, descricao.indexOf('.') + 1).trim();
                    }
                    
                    if (!descricao.equalsIgnoreCase(nomeFormatado)) {
                        resultado.add(nomeFormatado + ": " + descricao);
                    }
                }
            } finally {
                instances.close();
            }
        }
        
        return resultado.stream().distinct().sorted().collect(Collectors.toList());
    }
    
    private List<String> listarDefensiveTechniqueComRemocaoNome(String nomeArquivo) {
        OntModel ontology = carregarOntologia(nomeArquivo);
        List<String> resultado = new ArrayList<>();
        
        Property definitionProp = ontology.getProperty(D3FEND_NS + "definition");
        OntClass defensiveTechniqueClass = ontology.getOntClass(D3FEND_NS + "DefensiveTechnique");
        
        if (defensiveTechniqueClass != null && definitionProp != null) {
            ExtendedIterator<? extends OntResource> instances = defensiveTechniqueClass.listInstances(true);
            try {
                while (instances.hasNext()) {
                    OntResource instance = instances.next();
                    if (!instance.isURIResource()) continue;
                    
                    String uri = instance.getURI();
                    if (uri == null || !uri.startsWith(D3FEND_NS)) continue;
                    
                    String localName = uri.substring(D3FEND_NS.length());
                    if (!instance.hasRDFType(defensiveTechniqueClass)) continue;
                    
                    Statement stmt = instance.getProperty(definitionProp);
                    if (stmt == null || !stmt.getObject().isLiteral()) continue;
                    
                    String descricao = stmt.getLiteral().getString().trim();
                    if (descricao.isEmpty() || descricao.startsWith("Platforms includes") || descricao.length() < 10) continue;
                    
                    String nomeFormatado = formatarNomeCapitalizado(localName);
                    descricao = descricao.replaceAll("(?i)\\b" + Pattern.quote(nomeFormatado) + "\\b", "")
                                         .trim()
                                         .replaceAll("\\s{2,}", " ")
                                         .trim();
                    
                    if (!descricao.matches(".*\\b(and\\s*\\d+\\)).*")) {
                        resultado.add(nomeFormatado + ": " + descricao);
                    }
                }
            } finally {
                instances.close();
            }
        }
        
        return resultado.stream().distinct().sorted().collect(Collectors.toList());
    }
    
    private String formatarNomeCapitalizado(String localName) {
        String withSpaces = localName.replace("_", " ");
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < withSpaces.length(); i++) {
            char c = withSpaces.charAt(i);
            if (i > 0 && Character.isUpperCase(c) && withSpaces.charAt(i - 1) != ' ') {
                sb.append(' ');
            }
            sb.append(c);
        }
        
        String[] words = sb.toString().split(" ");
        sb.setLength(0);
        
        for (String word : words) {
            if (word.length() > 0) {
                sb.append(Character.toUpperCase(word.charAt(0)));
                if (word.length() > 1) {
                    sb.append(word.substring(1).toLowerCase());
                }
                sb.append(" ");
            }
        }
        
        return sb.toString().trim();
    }
    
    private String removerNomeDaDescricao(String nome, String descricao) {
        String regex = "(?i)^" + Pattern.quote(nome) + "\\s*[:\\-–]?\\s*";
        return descricao.replaceFirst(regex, "").trim();
    }
    
    public List<String> AttackEnterpriseMitigation() {
        return listarAttackEnterpriseMitigation("CyberSecSys.ttl", false);
    }
    
    public List<String> AttackEnterpriseMitigation_1() {
        return listarAttackEnterpriseMitigation("CyberSecSys_3.ttl", true);
    }
    
    private List<String> listarAttackEnterpriseMitigation(String nomeArquivo, boolean usarLabel) {
        OntModel ontology = carregarOntologia(nomeArquivo);
        List<String> resultado = new ArrayList<>();
        
        System.out.println("Buscando classe ATTACKEnterpriseMitigation...");
        OntClass attackClass = ontology.getOntClass(D3FEND_NS + "ATTACKEnterpriseMitigation");
        if (attackClass != null) {
            System.out.println("Classe ATTACKEnterpriseMitigation encontrada!");
            ExtendedIterator<? extends OntResource> instances = attackClass.listInstances();
            try {
                int count = 0;
                while (instances.hasNext()) {
                    Individual instance = (Individual) instances.next();
                    
                    if (usarLabel) {
                        String label = null;
                        if (instance.getLabel("en") != null) {
                            label = instance.getLabel("en");
                        } else if (instance.getLabel((String) null) != null) {
                            label = instance.getLabel((String) null);
                        } else {
                            StmtIterator labelStatements = instance.listProperties(RDFS.label);
                            try {
                                while (labelStatements.hasNext()) {
                                    Statement stmt = labelStatements.next();
                                    if (stmt.getObject().isLiteral()) {
                                        label = stmt.getObject().asLiteral().getString();
                                        break;
                                    }
                                }
                            } finally {
                                labelStatements.close();
                            }
                        }
                        
                        if (label != null && !label.trim().isEmpty()) {
                            resultado.add(label);
                            count++;
                            continue;
                        }
                    }
                    
                    String uri = instance.getURI();
                    if (uri != null && uri.startsWith(D3FEND_NS)) {
                        String localName = uri.substring(D3FEND_NS.length());
                        resultado.add(localName.replace("_", " "));
                        count++;
                    }
                }
                System.out.println("Encontradas " + count + " instâncias de ATTACKEnterpriseMitigation");
            } finally {
                instances.close();
            }
        } else {
            System.out.println("Classe ATTACKEnterpriseMitigation não encontrada");
        }
        
        Collections.sort(resultado);
        return resultado;
    }
    
    private String formatarNomeOSAPI_9(String nomeOriginal) {
        return nomeOriginal == null ? "" : nomeOriginal.replace("_", " ").trim();
    }
    
    public List<String> transformarInstancia(List<String> instancias) {
        if (instancias == null) return new ArrayList<>();
        return instancias.stream()
            .map(instancia -> instancia.toLowerCase().replace(" ", "_"))
            .collect(Collectors.toList());
    }
    
    public static List<String> buscarOSAPISystemFunctions(List<String> lista, String termoBusca) {
        List<String> resultadoNulo = Collections.singletonList("Was not found!");
        
        if (lista == null || lista.isEmpty() || termoBusca == null) {
            System.out.println("Lista vazia ou termo de busca nulo");
            return resultadoNulo;
        }
        
        String termoNormalizado = termoBusca.replaceAll("\\s+", "").toLowerCase();
        System.out.println("Buscando por: " + termoNormalizado);
        
        for (String item : lista) {
            if (item != null) {
                String itemNormalizado = item.replaceAll("\\s+", "").toLowerCase();
                if (itemNormalizado.contains(termoNormalizado)) {
                    System.out.println("Encontrado: " + item);
                    return Collections.singletonList(item);
                }
            }
        }
        
        return resultadoNulo;
    }
    
    public static List<String> buscarOSAPISystemFunctionsAlternativo(List<String> lista, String termoBusca) {
        List<String> resultadoNulo = Collections.singletonList("Was not found!");
        
        if (lista == null || lista.isEmpty() || termoBusca == null) {
            return resultadoNulo;
        }
        
        String termoSemEspacos = termoBusca.replaceAll("\\s+", "");
        Pattern p = Pattern.compile(Pattern.quote(termoSemEspacos), Pattern.CASE_INSENSITIVE);
        
        for (String item : lista) {
            if (item != null) {
                String itemSemEspacos = item.replaceAll("\\s+", "");
                if (p.matcher(itemSemEspacos).find()) {
                    return Collections.singletonList(item);
                }
            }
        }
        
        return resultadoNulo;
    }
    
    public List<String> OSAPISystemFunctions_8() {
        OntModel ontology = carregarOntologia("CyberSecSys.ttl");
        List<String> resultado = new ArrayList<>();
        
        String D3FEND_NS = "http://d3fend.mitre.org/ontologies/d3fend.owl#";
        OntClass osapiClass = ontology.getOntClass(D3FEND_NS + "OSAPISystemFunction");
        Property defProp = ontology.getProperty(D3FEND_NS + "definition");
        
        if (osapiClass != null) {
            ExtendedIterator<OntClass> subclasses = osapiClass.listSubClasses();
            try {
                while (subclasses.hasNext()) {
                    OntClass sub = subclasses.next();
                    String nome = sub.getLocalName();
                    if (nome != null && nome.startsWith("OSAPI")) {
                        String nomeFormatado = nome.replaceAll("(?<=[a-z])(?=[A-Z])|(?<=[A-Z])(?=[A-Z][a-z])", " ");
                        
                        String desc = "";
                        if (defProp != null) {
                            Statement stmt = sub.getProperty(defProp);
                            if (stmt != null && stmt.getObject().isLiteral())
                                desc = stmt.getLiteral().getString().trim();
                        }
                        if (desc.isEmpty()) {
                            Statement stmt = sub.getProperty(RDFS.comment);
                            if (stmt != null && stmt.getObject().isLiteral())
                                desc = stmt.getLiteral().getString().trim();
                        }
                        
                        resultado.add(desc.isEmpty() ? nomeFormatado : nomeFormatado + ": " + desc);
                    }
                }
            } finally {
                subclasses.close();
            }
        }
        
        return resultado.stream().distinct().sorted().collect(Collectors.toList());
    }
    
    
    public static void main(String[] args) {
        System.out.println("=== Iniciando testes CyberSecSys ===\n");
        
        CyberSecSys_2 c = new CyberSecSys_2();
        
        System.out.println("=== Testando métodos ===");
        
        List<String> funcoes = c.OSAPISystemFunctions_9();
      //  c.imprimirOSAPIFunctions();
      System.out.println("Funções OS API: " + funcoes);
        
        List<String> instanciasTransformadas = c.transformarInstancia(funcoes);
        System.out.println("Instâncias transformadas: " + instanciasTransformadas);
        
        String[] buscas = {
            "OS API Allocate Memory",
            "osapiappallocateMemory",
            "OSAPIALLOCATEMEMORY",
            "allocate memory"
        };
        
        for (int i = 0; i < buscas.length; i++) {
            List<String> busca = buscarOSAPISystemFunctions(funcoes, buscas[i]);
            System.out.println("Busca " + (i + 1) + " (" + buscas[i] + "): " + busca);
        }
        
        System.out.println("\n=== Defensive Techniques ===");
        List<String> defensive = c.DefensiveTechnique_5();
        defensive.stream().limit(5).forEach(System.out::println);
        System.out.println("Total: " + defensive.size());
    }
}