package servlets_1;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.ontology.OntResource;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.jena.vocabulary.DC_11;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDFS;
import org.apache.jena.vocabulary.SKOS;

public class CyberSecSys {
    
    private static final String BASE_NS = "http://www.ppgi.uniriotec.br/CyberSecSys_1.0#";
    private static final String D3FEND_NS = "http://d3fend.mitre.org/ontologies/d3fend.owl#";
    private static final String DEFAULT_FILE = "CyberSecSys.ttl";
    
    private OntModel carregarOntologia(String fileName) {
        OntModel ontology = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        try {
            ClassLoader classLoader = this.getClass().getClassLoader();
            URL resource = classLoader.getResource(fileName);
            if (resource == null) {
                throw new IllegalArgumentException("Arquivo não encontrado: " + fileName);
            }
            Path path = Paths.get(resource.toURI());
            ontology.read(path.toUri().toString(), "Turtle");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar ontologia: " + e.getMessage(), e);
        }
        return ontology;
    }
    
    private List<String> listarInstanciasDaClasse(String className, String fileName, String namespace) {
        OntModel ontology = carregarOntologia(fileName);
        List<String> resultado = new ArrayList<>();
        
        OntClass classe = ontology.getOntClass(namespace + className);
        if (classe != null) {
            ExtendedIterator<? extends OntResource> instances = classe.listInstances();
            while (instances.hasNext()) {
                OntResource instance = instances.next();
                String localName = instance.getLocalName();
                if (localName != null) {
                    resultado.add(localName.replace("_", " "));
                }
            }
            instances.close();
        } else {
            System.out.println("Classe " + className + " não encontrada na ontologia");
        }
        
        Collections.sort(resultado);
        return resultado;
    }
    
    private List<String> listarInstanciasDaClasseComDescricao(String className, String fileName, String namespace) {
        OntModel ontology = carregarOntologia(fileName);
        List<String> resultado = new ArrayList<>();
        
        OntClass classe = ontology.getOntClass(namespace + className);
        if (classe != null) {
            ExtendedIterator<? extends OntResource> instances = classe.listInstances();
            while (instances.hasNext()) {
                OntResource instance = instances.next();
                String nome = instance.getLocalName();
                if (nome != null) {
                    String nomeFormatado = formatarNomeBasico(nome);
                    String descricao = extrairDescricao(instance, ontology, namespace);
                    if (descricao.isEmpty()) {
                        descricao = "Sem descrição disponível.";
                    }
                    resultado.add(nomeFormatado + " — " + descricao);
                }
            }
            instances.close();
        }
        
        Collections.sort(resultado);
        return resultado;
    }
    
    private String extrairDescricao(OntResource recurso, OntModel modelo, String namespace) {
        Property[] propriedades = {
            modelo.getProperty(namespace + "definition"),
            modelo.getProperty(namespace + "descricao"),
            modelo.getProperty(namespace + "description"),
            SKOS.definition,
            RDFS.comment,
            OWL.versionInfo,
            DC_11.description
        };
        
        for (Property p : propriedades) {
            if (p != null) {
                Statement stmt = recurso.getProperty(p);
                if (stmt != null && stmt.getObject().isLiteral()) {
                    String desc = stmt.getLiteral().getString().trim();
                    if (!desc.isEmpty()) {
                        return desc;
                    }
                }
            }
        }
        return "";
    }
    
    private String formatarNomeBasico(String nome) {
        if (nome == null) return "";
        String processado = nome.replace("_", " ").trim();
        processado = processado.replaceAll("(?<=[a-z])([A-Z])", " $1");
        return processado.replaceAll("\\s+", " ").trim();
    }
    
    private String formatarNomeOSAPI(String nomeOriginal) {
        if (nomeOriginal == null) return "";
        String processado = nomeOriginal.replace("OSAPI", "OS API").replace("_", " ");
        processado = processado.replaceAll("(?<=[a-z])([A-Z])", " $1");
        return processado.replaceAll("\\s+", " ").trim();
    }
    
    private String formatarNomeD3FEND(String localName) {
        if (localName == null) return "";
        
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
    
    // Métodos principais
    
    public List<String> Approach() {
        return listarInstanciasDaClasse("Approach", DEFAULT_FILE, BASE_NS);
    }
    
    public List<String> PerformanceEvaluation() {
        return listarInstanciasDaClasse("PerformanceEvaluation", DEFAULT_FILE, BASE_NS);
    }
    
    public List<String> Component() {
        return listarInstanciasDaClasse("Component", DEFAULT_FILE, BASE_NS);
    }
    
    public List<String> Systens() {
        String[] classesSistema = {
            "Collaborative_Systems_Component",
            "Cyber-PhysicalSystem_Component", 
            "IntelligentSystem_Component",
            "System-Of-Systems_Component"
        };
        
        OntModel ontology = carregarOntologia("CyberSecSys_3.ttl");
        LinkedHashSet<String> uniqueList = new LinkedHashSet<>();
        
        ExtendedIterator<OntClass> classIterator = ontology.listClasses();
        while (classIterator.hasNext()) {
            OntClass c = classIterator.next();
            String localName = c.getLocalName();
            
            if (localName != null) {
                for (String classeSistema : classesSistema) {
                    if (localName.equals(classeSistema)) {
                        ExtendedIterator<? extends OntResource> instances = c.listInstances();
                        while (instances.hasNext()) {
                            OntResource i = instances.next();
                            String nome = i.getLocalName();
                            if (nome != null) {
                                uniqueList.add(nome);
                            }
                        }
                        instances.close();
                        break;
                    }
                }
            }
        }
        classIterator.close();
        
        List<String> resultado = new ArrayList<>(uniqueList);
        Collections.sort(resultado);
        
        List<String> nomesProcessados = new ArrayList<>();
        for (String item : resultado) {
            String processado = item
                .replace("_Component", "")
                .replace("Component_", "")
                .replace("_", " ")
                .replaceAll("\\bComponent\\b", "")
                .trim()
                .replaceAll("\\s+", " ");
            processado = processado.replaceAll("(?<=[a-z])([A-Z])", " $1");
            if (!processado.isEmpty()) {
                nomesProcessados.add(processado);
            }
        }
        
        return nomesProcessados;
    }
    
    public List<String> OSAPISystemFunctions_9() {
        OntModel ontology = carregarOntologia(DEFAULT_FILE);
        LinkedHashSet<String> uniqueList = new LinkedHashSet<>();
        
        ExtendedIterator<OntClass> classIterator = ontology.listClasses();
        while (classIterator.hasNext()) {
            OntClass c = classIterator.next();
            if (c.getLocalName() != null && c.getLocalName().equals("OSAPISystemFunction")) {
                ExtendedIterator<? extends OntResource> instances = c.listInstances();
                while (instances.hasNext()) {
                    OntResource i = instances.next();
                    String nome = i.getLocalName();
                    if (nome != null) {
                        uniqueList.add(nome);
                    }
                }
                instances.close();
                break;
            }
        }
        classIterator.close();
        
        List<String> resultado = new ArrayList<>(uniqueList);
        Collections.sort(resultado);
        resultado = resultado.stream()
                .map(this::formatarNomeOSAPI)
                .collect(Collectors.toList());
        
        return resultado;
    }
    
    public List<String> OSAPISystemFunctions_2() {
        return listarInstanciasDaClasseComDescricao("OSAPISystemFunction", DEFAULT_FILE, BASE_NS);
    }
    
    public List<String> OSAPISystemFunctions_3() {
        OntModel ontology = carregarOntologia(DEFAULT_FILE);
        LinkedHashSet<String> uniqueList = new LinkedHashSet<>();
        
        ExtendedIterator<OntClass> classIterator = ontology.listClasses();
        while (classIterator.hasNext()) {
            OntClass c = classIterator.next();
            if (c.getLocalName() != null && c.getLocalName().equals("OSAPISystemFunction")) {
                ExtendedIterator<? extends OntResource> instances = c.listInstances();
                while (instances.hasNext()) {
                    OntResource i = instances.next();
                    String nome = i.getLocalName();
                    if (nome != null) {
                        uniqueList.add(formatarNomeBasico(nome));
                    }
                }
                instances.close();
                break;
            }
        }
        classIterator.close();
        
        List<String> resultado = new ArrayList<>(uniqueList);
        Collections.sort(resultado);
        return resultado;
    }
    
    
    public List<String> OSAPISystemFunctions_4() {
        OntModel ontology = carregarOntologia("CyberSecSys_1.ttl");
        List<String> resultado = new ArrayList<>();
        Map<String, String> funcoesMap = new LinkedHashMap<>();
        
        // Propriedade de definição do D3FEND
        Property definitionProp = ontology.getProperty("http://d3fend.mitre.org/ontologies/d3fend.owl#definition");
        
        // Busca todos os indivíduos
        ExtendedIterator<Individual> individualIterator = ontology.listIndividuals();
        
        while (individualIterator.hasNext()) {
            Individual ind = individualIterator.next();
            String localName = ind.getLocalName();
            
            // Filtra apenas funções OSAPI
            if (localName != null && localName.startsWith("OSAPI")) {
                
                // CORREÇÃO: Formata o nome corretamente
                String nomeFormatado = localName
                    .replace("OSAPI", "OS API")  // Primeiro substitui o prefixo
                    .replaceAll("([a-z])([A-Z])", "$1 $2")  // Adiciona espaço entre minúscula e maiúscula
                    .replaceAll("([A-Z])([A-Z][a-z])", "$1 $2");  // Trata casos como "APIAllocate" -> "API Allocate"
                
                String definicao = "";
                
                // Tenta buscar a definição
                if (definitionProp != null) {
                    StmtIterator stmtIter = ind.listProperties(definitionProp);
                    if (stmtIter.hasNext()) {
                        Statement stmt = stmtIter.next();
                        if (stmt.getObject().isLiteral()) {
                            definicao = stmt.getLiteral().getString().trim();
                            // Remove ponto final se existir
                            if (definicao.endsWith(".")) {
                                definicao = definicao.substring(0, definicao.length() - 1);
                            }
                        }
                    }
                }
                
                // Se não achou definição, usa um texto padrão
                if (definicao.isEmpty()) {
                    definicao = "OS API function";
                }
                
                // Armazena no mapa para garantir unicidade
                funcoesMap.put(nomeFormatado, definicao);
            }
        }
        individualIterator.close();
        
        // Converte o mapa para lista no formato solicitado
        for (Map.Entry<String, String> entry : funcoesMap.entrySet()) {
            resultado.add(entry.getKey() + " - " + entry.getValue());
        }
        
        // Ordena alfabeticamente
        Collections.sort(resultado);
        
        return resultado;
    }
    
    public void imprimirOSAPIFunctions() {
        List<String> funcoes = OSAPISystemFunctions();
        
        System.out.println("=" .repeat(90));
        System.out.println("🔍 FUNÇÕES OS API COM DEFINIÇÕES");
        System.out.println("=" .repeat(90));
        System.out.println();
        
        for (int i = 0; i < funcoes.size(); i++) {
            System.out.println((i + 1) + ". " + funcoes.get(i));
        }
        
        System.out.println();
        System.out.println("-" .repeat(90));
        System.out.println("📊 TOTAL: " + funcoes.size() + " funções OS API encontradas");
        System.out.println("=" .repeat(90));
    }
    
    
    // Método de teste rápido
    public void testarOSAPIFunctions() {
        List<String> funcoes = OSAPISystemFunctions();
        
        System.out.println("Primeiras 10 funções OS API:");
        System.out.println("-" .repeat(50));
        
        for (int i = 0; i < Math.min(10, funcoes.size()); i++) {
            System.out.println((i + 1) + ". " + funcoes.get(i));
        }
        
        System.out.println("\n... e mais " + (funcoes.size() - 10) + " funções");
    }
    
    
    public List<String> OSAPISystemFunctions_51() {
        OntModel ontology = carregarOntologia("CyberSecSys.ttl");
        
        return ontology.listIndividuals()
            .filterKeep(individual -> {
                String name = individual.getLocalName();
                return name != null && name.startsWith("OSAPI");
            })
            .mapWith(individual -> {
                String nomeFormatado = individual.getLocalName().replace("_", " ");
                
                // Tentar buscar descrição
                String descricao = "";
                Statement commentStmt = individual.getProperty(RDFS.comment);
                if (commentStmt != null && commentStmt.getObject().isLiteral()) {
                    descricao = commentStmt.getLiteral().getString().trim();
                }
                
                if (!descricao.isEmpty()) {
                    return nomeFormatado + ": " + descricao;
                } else {
                    return nomeFormatado;
                }
            })
            .toList()
            .stream()
            .distinct()
            .sorted()
            .collect(Collectors.toList());
    }
    
    public List<String> OSAPISystemFunctions() {
        OntModel ontology = carregarOntologia("CyberSecSys.ttl");
        List<String> resultado = new ArrayList<>();
        
        String D3FEND_NS = "http://d3fend.mitre.org/ontologies/d3fend.owl#";
        
        OntClass osapiSystemFunctionClass = ontology.getOntClass(D3FEND_NS + "OSAPISystemFunction");
        Property definitionProp = ontology.getProperty(D3FEND_NS + "definition");
        
        if (osapiSystemFunctionClass != null) {
            ExtendedIterator<OntClass> subclasses = osapiSystemFunctionClass.listSubClasses();
            
            try {
                while (subclasses.hasNext()) {
                    OntClass subclasse = subclasses.next();
                    String localName = subclasse.getLocalName();
                    
                    if (localName != null && localName.startsWith("OSAPI")) {
                        String nomeFormatado = localName.replace("_", " ");
                        
                        // Buscar descrição em múltiplas fontes
                        String descricao = buscarDescricao(subclasse, definitionProp, ontology);
                        
                        if (!descricao.isEmpty()) {
                            resultado.add(nomeFormatado + ": " + descricao);
                        } else {
                            resultado.add(nomeFormatado);
                        }
                    }
                }
            } finally {
                subclasses.close();
            }
        }
        
        return resultado.stream().distinct().sorted().collect(Collectors.toList());
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

    private String buscarDescricao(OntClass classe, Property definitionProp, OntModel ontology) {
        // 1. Tentar na própria classe via definition
        if (definitionProp != null) {
            Statement defStmt = classe.getProperty(definitionProp);
            if (defStmt != null && defStmt.getObject().isLiteral()) {
                String desc = defStmt.getLiteral().getString().trim();
                if (!desc.isEmpty()) return desc;
            }
        }
        
        // 2. Tentar na classe via rdfs:comment
        Statement commentStmt = classe.getProperty(RDFS.comment);
        if (commentStmt != null && commentStmt.getObject().isLiteral()) {
            String desc = commentStmt.getLiteral().getString().trim();
            if (!desc.isEmpty()) return desc;
        }
        
        // 3. Tentar nos indivíduos da classe
        ExtendedIterator<? extends OntResource> instances = classe.listInstances(true);
        try {
            while (instances.hasNext()) {
                OntResource instance = instances.next();
                
                // 3.1 Definition no indivíduo
                if (definitionProp != null) {
                    Statement instDefStmt = instance.getProperty(definitionProp);
                    if (instDefStmt != null && instDefStmt.getObject().isLiteral()) {
                        String desc = instDefStmt.getLiteral().getString().trim();
                        if (!desc.isEmpty()) return desc;
                    }
                }
                
                // 3.2 Comment no indivíduo
                Statement instCommentStmt = instance.getProperty(RDFS.comment);
                if (instCommentStmt != null && instCommentStmt.getObject().isLiteral()) {
                    String desc = instCommentStmt.getLiteral().getString().trim();
                    if (!desc.isEmpty()) return desc;
                }
            }
        } finally {
            instances.close();
        }
        
        return "";
    }

    public List<String> OSAPISystemFunctions_7() {
        OntModel ontology = carregarOntologia("CyberSecSys.ttl");
        List<String> resultado = new ArrayList<>();
        
        // Namespace da ontologia D3FEND
        String D3FEND_NS = "http://d3fend.mitre.org/ontologies/d3fend.owl#";
        
        // Buscar a classe OSAPISystemFunction no D3FEND
        OntClass osapiSystemFunctionClass = ontology.getOntClass(D3FEND_NS + "OSAPISystemFunction");
        
        // Propriedade de definição do D3FEND
        Property definitionProp = ontology.getProperty(D3FEND_NS + "definition");
        
        if (osapiSystemFunctionClass != null) {
            // Listar todas as subclasses de OSAPISystemFunction
            ExtendedIterator<OntClass> subclasses = osapiSystemFunctionClass.listSubClasses();
            
            try {
                while (subclasses.hasNext()) {
                    OntClass subclasse = subclasses.next();
                    
                    // Pegar o nome local da subclasse
                    String localName = subclasse.getLocalName();
                    if (localName != null && localName.startsWith("OSAPI")) {
                        String nomeFormatado = localName.replace("_", " ");
                        
                        // Buscar a definição da subclasse
                        String descricao = "";
                        
                        // Tentar obter a definição via propriedade "definition"
                        if (definitionProp != null) {
                            Statement stmt = subclasse.getProperty(definitionProp);
                            if (stmt != null && stmt.getObject().isLiteral()) {
                                descricao = stmt.getLiteral().getString().trim();
                            }
                        }
                        
                        // Se não encontrou definition, tentar rdfs:comment
                        if (descricao.isEmpty()) {
                            Statement commentStmt = subclasse.getProperty(RDFS.comment);
                            if (commentStmt != null && commentStmt.getObject().isLiteral()) {
                                descricao = commentStmt.getLiteral().getString().trim();
                            }
                        }
                        
                        // Se ainda não tem descrição, buscar nos indivíduos da subclasse
                        if (descricao.isEmpty()) {
                            ExtendedIterator<? extends OntResource> instances = subclasse.listInstances(true);
                            try {
                                if (instances.hasNext()) {
                                    OntResource instance = instances.next();
                                    if (definitionProp != null) {
                                        Statement stmt = instance.getProperty(definitionProp);
                                        if (stmt != null && stmt.getObject().isLiteral()) {
                                            descricao = stmt.getLiteral().getString().trim();
                                        }
                                    }
                                }
                            } finally {
                                instances.close();
                            }
                        }
                        
                        // Adicionar ao resultado
                        if (!descricao.isEmpty()) {
                            // Limpar a descrição (remover repetições do nome)
                            descricao = descricao.replace(localName, "").replace("_", " ").trim();
                            if (descricao.startsWith(":")) {
                                descricao = descricao.substring(1).trim();
                            }
                            resultado.add(nomeFormatado + ": " + descricao);
                        } else {
                            resultado.add(nomeFormatado);
                        }
                    }
                }
            } finally {
                subclasses.close();
            }
        }
        
        return resultado.stream().distinct().sorted().collect(Collectors.toList());
    }
    
    public List<String> OSAPISystemFunctions_6() {
        OntModel ontology = carregarOntologia("CyberSecSys_1.ttl");
        List<String> resultado = new ArrayList<>();
        
        System.out.println("=== DEBUG OSAPISystemFunctions_5 ===");
        
        // Namespace correto da ontologia CyberSecSys
        String ns = "http://www.ppgi.uniriotec.br/CyberSecSys_1.0#";
        System.out.println("Namespace: " + ns);
        
        // Buscar a classe OSAPISystemFunction no namespace correto
        System.out.println("Buscando classe: " + ns + "OSAPISystemFunction");
        OntClass osapiClass = ontology.getOntClass(ns + "OSAPISystemFunction");
        
        if (osapiClass == null) {
            System.out.println("Classe não encontrada! Listando todas as classes disponíveis:");
            ExtendedIterator<OntClass> classes = ontology.listClasses();
            try {
                while (classes.hasNext()) {
                    OntClass c = classes.next();
                    if (c.getURI() != null) {
                        System.out.println("  - " + c.getURI());
                        // Verificar se contém OSAPISystemFunction no URI
                        if (c.getURI().contains("OSAPISystemFunction")) {
                            System.out.println("    >>> ENCONTRADA! " + c.getURI());
                            osapiClass = c;
                        }
                    }
                }
            } finally {
                classes.close();
            }
        }
        
        if (osapiClass != null) {
            System.out.println("Classe encontrada: " + osapiClass.getURI());
            
           
            
            // Propriedade de definição
            Property definitionProp = ontology.getProperty(ns + "definition");
            System.out.println("Propriedade definition: " + (definitionProp != null ? definitionProp.getURI() : "null"));
            
            ExtendedIterator<? extends OntResource> instances = osapiClass.listInstances(true);
            try {
                int count = 0;
                while (instances.hasNext()) {
                    OntResource instance = instances.next();
                    String uri = instance.getURI();
                    String nomeOriginal = instance.getLocalName();
                    System.out.println("\nInstância " + (++count) + ":");
                    System.out.println("  URI: " + uri);
                    System.out.println("  LocalName: " + nomeOriginal);
                    
                    if (nomeOriginal != null) {
                        // Listar todas as propriedades da instância
                        System.out.println("  Propriedades da instância:");
                        StmtIterator stmts = instance.listProperties();
                        try {
                            while (stmts.hasNext()) {
                                Statement stmt = stmts.next();
                                System.out.println("    " + stmt.getPredicate().getURI() + " = " + stmt.getObject());
                            }
                        } finally {
                            stmts.close();
                        }
                        
                        // Buscar descrição
                        String descricao = "";
                        
                        // Tentar definition
                        if (definitionProp != null) {
                            Statement stmt = instance.getProperty(definitionProp);
                            if (stmt != null && stmt.getObject().isLiteral()) {
                                descricao = stmt.getLiteral().getString().trim();
                                System.out.println("  Definition encontrada: " + descricao);
                            }
                        }
                        
                        // Se não encontrou definition, tentar rdfs:comment
                        if (descricao.isEmpty()) {
                            Statement stmt = instance.getProperty(RDFS.comment);
                            if (stmt != null && stmt.getObject().isLiteral()) {
                                descricao = stmt.getLiteral().getString().trim();
                                System.out.println("  Comment encontrado: " + descricao);
                            }
                        }
                        
                        // Formatar o nome da instância
                        String nomeFormatado = nomeOriginal.replace("_", " ");
                        
                        // Adicionar ao resultado
                        if (!descricao.isEmpty()) {
                            resultado.add(nomeFormatado + ": " + descricao);
                        } else {
                            resultado.add(nomeFormatado);
                        }
                    }
                }
                System.out.println("\nTotal de instâncias encontradas: " + count);
            } finally {
                instances.close();
            }
        } else {
            System.out.println("ERRO: Classe OSAPISystemFunction não encontrada na ontologia!");
        }
        
        System.out.println("Resultados finais: " + resultado.size());
        System.out.println("=====================================\n");
        
        return resultado.stream().distinct().sorted().collect(Collectors.toList());
    }
    
    public List<String> DefensiveTechnique() {
        OntModel ontology = carregarOntologia("CyberSecSys_2.ttl");
        List<String> resultado = new ArrayList<>();
        
        OntClass defensiveTechniqueClass = ontology.getOntClass(D3FEND_NS + "DefensiveTechnique");
        if (defensiveTechniqueClass != null) {
            ExtendedIterator<? extends OntResource> instances = defensiveTechniqueClass.listInstances();
            while (instances.hasNext()) {
                OntResource instance = instances.next();
                String uri = instance.getURI();
                if (uri != null && uri.startsWith(D3FEND_NS)) {
                    String localName = uri.substring(D3FEND_NS.length());
                    resultado.add(formatarNomeBasico(localName));
                }
            }
            instances.close();
        }
        
        Collections.sort(resultado);
        return resultado;
    }
    
    
    
    
    public List<String> DefensiveTechnique_2() {
        OntModel ontology = carregarOntologia("CyberSecSys_2.ttl");
        List<String> resultado = new ArrayList<>();
        
        OntClass defensiveTechniqueClass = ontology.getOntClass(D3FEND_NS + "DefensiveTechnique");
        if (defensiveTechniqueClass != null) {
            ExtendedIterator<? extends OntResource> instances = defensiveTechniqueClass.listInstances();
            while (instances.hasNext()) {
                OntResource instance = instances.next();
                String uri = instance.getURI();
                if (uri != null && uri.startsWith(D3FEND_NS)) {
                    String localName = uri.substring(D3FEND_NS.length());
                    String nomeFormatado = formatarNomeD3FEND(localName);
                    String descricao = extrairDescricao(instance, ontology, D3FEND_NS);
                    if (descricao.isEmpty()) {
                        descricao = " ";
                    }
                    resultado.add(nomeFormatado + " — " + descricao);
                }
            }
            instances.close();
        }
        
        Collections.sort(resultado);
        return resultado;
    }
    
    public List<String> DefensiveTechnique_4() {
        OntModel ontology = carregarOntologia("CyberSecSys_4.ttl");
        List<String> resultado = new ArrayList<>();
        
        OntClass defensiveTechniqueClass = ontology.getOntClass(D3FEND_NS + "DefensiveTechnique");
        Property definitionProp = ontology.getProperty(D3FEND_NS + "definition");
        
        if (defensiveTechniqueClass != null) {
            ExtendedIterator<? extends OntResource> instances = defensiveTechniqueClass.listInstances(true);
            while (instances.hasNext()) {
                OntResource instance = instances.next();
                if (instance.isURIResource()) {
                    String uri = instance.getURI();
                    if (uri != null && uri.startsWith(D3FEND_NS) && instance.hasRDFType(defensiveTechniqueClass)) {
                        Statement stmt = instance.getProperty(definitionProp);
                        if (stmt != null && stmt.getObject().isLiteral()) {
                            String descricao = stmt.getLiteral().getString().trim();
                            if (!descricao.isEmpty() && !descricao.startsWith("Platforms includes") && descricao.length() >= 10) {
                                String localName = uri.substring(D3FEND_NS.length());
                                String nomeFormatado = formatarNomeD3FEND(localName);
                                resultado.add(nomeFormatado + ": " + descricao);
                            }
                        }
                    }
                }
            }
            instances.close();
        }
        
        return resultado.stream().distinct().sorted().collect(Collectors.toList());
    }
    
    public List<String> DefensiveTechnique_5() {
        OntModel ontology = carregarOntologia("CyberSecSys_4.ttl");
        List<String> resultado = new ArrayList<>();
        
        OntClass defensiveTechniqueClass = ontology.getOntClass(D3FEND_NS + "DefensiveTechnique");
        Property definitionProp = ontology.getProperty(D3FEND_NS + "definition");
        
        if (defensiveTechniqueClass != null) {
            ExtendedIterator<? extends OntResource> instances = defensiveTechniqueClass.listInstances(true);
            while (instances.hasNext()) {
                OntResource instance = instances.next();
                if (instance.isURIResource()) {
                    String uri = instance.getURI();
                    if (uri != null && uri.startsWith(D3FEND_NS) && instance.hasRDFType(defensiveTechniqueClass)) {
                        Statement stmt = instance.getProperty(definitionProp);
                        if (stmt != null && stmt.getObject().isLiteral()) {
                            String descricao = stmt.getLiteral().getString().trim();
                            if (!descricao.isEmpty() && !descricao.startsWith("Platforms includes") && descricao.length() >= 10) {
                                String localName = uri.substring(D3FEND_NS.length());
                                String nomeFormatado = formatarNomeD3FEND(localName);
                                descricao = removerNomeDaDescricao(nomeFormatado, descricao);
                                resultado.add(nomeFormatado + ": " + descricao);
                            }
                        }
                    }
                }
            }
            instances.close();
        }
        
        return resultado.stream().distinct().sorted().collect(Collectors.toList());
    }
    
    public List<String> DefensiveTechnique_7() {
        OntModel ontology = carregarOntologia("CyberSecSys_4.ttl");
        List<String> resultado = new ArrayList<>();
        
        OntClass defensiveTechniqueClass = ontology.getOntClass(D3FEND_NS + "DefensiveTechnique");
        if (defensiveTechniqueClass != null) {
            ExtendedIterator<? extends OntResource> instances = defensiveTechniqueClass.listInstances();
            while (instances.hasNext()) {
                OntResource instance = instances.next();
                String uri = instance.getURI();
                if (uri != null && uri.startsWith(D3FEND_NS)) {
                    String localName = uri.substring(D3FEND_NS.length());
                    String nomeFormatado = formatarNomeD3FEND(localName);
                    String descricao = extrairDescricao(instance, ontology, D3FEND_NS);
                    if (descricao.isEmpty()) {
                        descricao = "Sem descrição disponível.";
                    }
                    resultado.add(nomeFormatado + " - " + descricao);
                }
            }
            instances.close();
        }
        
        Collections.sort(resultado);
        return resultado;
    }
    
    private String removerNomeDaDescricao(String nome, String descricao) {
        String regex = "(?i)^" + Pattern.quote(nome) + "\\s*[:\\-–]?\\s*";
        return descricao.replaceFirst(regex, "").trim();
    }
    
    public List<String> AttackEnterpriseMitigation() {
        OntModel ontology = carregarOntologia("CyberSecSys_3.ttl");
        List<String> resultado = new ArrayList<>();
        
        OntClass attackClass = ontology.getOntClass(D3FEND_NS + "ATTACKEnterpriseMitigation");
        if (attackClass != null) {
            ExtendedIterator<? extends OntResource> instances = attackClass.listInstances();
            while (instances.hasNext()) {
                OntResource instance = instances.next();
                String uri = instance.getURI();
                
                String label = null;
                if (instance.getLabel("en") != null) {
                    label = instance.getLabel("en");
                } else if (instance.getLabel(null) != null) {
                    label = instance.getLabel(null);
                } else {
                    StmtIterator labelStatements = instance.listProperties(RDFS.label);
                    while (labelStatements.hasNext()) {
                        Statement stmt = labelStatements.next();
                        if (stmt.getObject().isLiteral()) {
                            label = stmt.getObject().asLiteral().getString();
                            break;
                        }
                    }
                    labelStatements.close();
                }
                
                if (label != null && !label.trim().isEmpty()) {
                    resultado.add(label);
                } else if (uri != null && uri.startsWith(D3FEND_NS)) {
                    String localName = uri.substring(D3FEND_NS.length());
                    resultado.add(formatarNomeBasico(localName));
                }
            }
            instances.close();
        }
        
        Collections.sort(resultado);
        return resultado;
    }
    
    public List<String> AttackEnterpriseMitigation_1() {
        OntModel ontology = carregarOntologia("CyberSecSys_3.ttl");
        List<String> resultado = new ArrayList<>();
        
        OntClass attackClass = ontology.getOntClass(D3FEND_NS + "ATTACKEnterpriseMitigation");
        if (attackClass != null) {
            ExtendedIterator<? extends OntResource> instances = attackClass.listInstances();
            while (instances.hasNext()) {
                OntResource instance = instances.next();
                String uri = instance.getURI();
                
                String nome = null;
                if (uri != null && uri.startsWith(D3FEND_NS)) {
                    String localName = uri.substring(D3FEND_NS.length());
                    nome = formatarNomeBasico(localName);
                }
                
                String descricao = extrairDescricao(instance, ontology, D3FEND_NS);
                if (descricao.isEmpty()) {
                    descricao = "Sem descrição disponível.";
                }
                
                if (nome != null) {
                    resultado.add(nome + " — " + descricao);
                }
            }
            instances.close();
        }
        
        Collections.sort(resultado);
        return resultado;
    }
    
    // Métodos de busca
    
    public static List<String> buscarOSAPISystemFunctions(List<String> lista, String termoBusca) {
        List<String> resultadoNulo = Collections.singletonList("Was not found!");
        
        if (lista == null || termoBusca == null) {
            return resultadoNulo;
        }
        
        String termoNormalizado = termoBusca.replaceAll("\\s+", "").toLowerCase();
        
        for (String item : lista) {
            if (item != null) {
                String itemNormalizado = item.replaceAll("\\s+", "").toLowerCase();
                if (itemNormalizado.contains(termoNormalizado)) {
                    return Collections.singletonList(item);
                }
            }
        }
        
        return resultadoNulo;
    }
    
    public static List<String> buscarOSAPISystemFunctionsAlternativo(List<String> lista, String termoBusca) {
        List<String> resultadoNulo = Collections.singletonList("Was not found!");
        
        if (lista == null || termoBusca == null) {
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
    
    public List<String> transformarInstancia(List<String> instancias) {
        if (instancias == null) return new ArrayList<>();
        
        return instancias.stream()
                .map(instancia -> instancia.toLowerCase().replace(" ", "_"))
                .collect(Collectors.toList());
    }
    
    public static void main(String[] args) {
        CyberSecSys c = new CyberSecSys();
        
        List<String> osapiFunctions = c.OSAPISystemFunctions();
        
        osapiFunctions.stream().forEach(System.out::println);
      //  System.out.println("Total: " + osapiFunctions.size());
    }
}