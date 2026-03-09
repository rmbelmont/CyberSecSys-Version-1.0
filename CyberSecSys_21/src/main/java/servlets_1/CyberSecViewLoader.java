package servlets_1;
import java.util.List;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class CyberSecViewLoader implements ServletContextListener {
    private Thread loadThread;
    private volatile boolean running = true;
    private static volatile boolean viewLoaded = false;
    private static volatile boolean viewLoading = false;
    private static CyberSecViewLoader instance;

    public CyberSecViewLoader() {
        instance = this;
        log("🔧 CyberSecViewLoader instantiated.");
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log("🔄 Initializing CyberSecView...");
        
        synchronized (CyberSecViewLoader.class) {
            if (viewLoading) {
                log("⚠️ View is already loading. Ignoring duplicate request.");
                return;
            }
            viewLoading = true;
            viewLoaded = false;
        }
        
        this.running = true;
        this.loadThread = new Thread(() -> {
            this.loadCyberSecView(sce);
        }, "CyberSecView-Loader-Thread");
        
        this.loadThread.setDaemon(true);
        this.loadThread.start();
        log("🚀 Background loading started (thread: " + this.loadThread.getName() + ")");
    }

    private void loadCyberSecView(ServletContextEvent sce) {
        try {
            log("🎯 Creating CyberSecView instance...");
            CyberSecView view = CyberSecView.getInstance();
            
            log("📦 Loading ontology data...");
            view.carregarDados();
            
            if (!this.running) {
                log("⚠️ Loading aborted (context destroyed).");
                return;
            }

            sce.getServletContext().setAttribute("cyberSecView", view);
            
            synchronized (CyberSecViewLoader.class) {
                viewLoaded = true;
                viewLoading = false;
            }
            
            log("✅ CyberSecView successfully loaded into context.");
            log("📊 Status: " + view.getStatus());
            
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            
            this.logDetails(view);
            
        } catch (Exception e) {
            this.handleLoadingError(e, sce);
        } finally {
            log("🏁 Loader thread finished.");
        }
    }

    private void handleLoadingError(Exception e, ServletContextEvent sce) {
        synchronized (CyberSecViewLoader.class) {
            viewLoading = false;
        }
        
        logError("❌ Critical error loading CyberSecView", e);
        
        if (!this.running) {
            log("⚠️ Error ignored (context already destroyed).");
            return;
        }
        
        try {
            log("🆘 Creating fallback CyberSecView...");
            CyberSecView fallback = CyberSecView.getInstance();
            sce.getServletContext().setAttribute("cyberSecView", fallback);
            log("⚠️ Fallback view available (with limited data). Status: " + fallback.getStatus());
        } catch (Exception ex) {
            logError("💥 Failed to create fallback CyberSecView", ex);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log("🧹 Context destroyed — starting cleanup.");
        this.running = false;
        
        if (loadThread != null && loadThread.isAlive()) {
            try {
                log("⏳ Waiting for loader thread to finish...");
                loadThread.interrupt();
                loadThread.join(5000L);
                
                if (loadThread.isAlive()) {
                    log("⚠️ Loader thread still active after timeout.");
                } else {
                    log("✅ Loader thread finished.");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logError("❌ Interrupted while waiting for loader thread.", e);
            }
        }

        synchronized (CyberSecViewLoader.class) {
            if (!viewLoaded) {
                sce.getServletContext().removeAttribute("cyberSecView");
                log("🧹 Removed incomplete CyberSecView from context.");
            } else {
                log("🔒 CyberSecView retained in memory for reuse.");
                log("📊 Final status: " + getViewStatusFromContext(sce));
            }
            
            viewLoaded = false;
            viewLoading = false;
        }
        
        log("✅ Cleanup completed. Ready for redeploy.");
    }

    private String getViewStatusFromContext(ServletContextEvent sce) {
        try {
            CyberSecView view = (CyberSecView) sce.getServletContext().getAttribute("cyberSecView");
            return view != null ? view.getStatus() : "NOT_FOUND";
        } catch (Exception e) {
            return "ERROR_ACCESSING_VIEW: " + e.getMessage();
        }
    }

    private void logDetails(CyberSecView view) {
        if (view != null) {
            log("📈 Loaded data summary:");
            log("   - Approach: " + (view.getApproach() != null ? view.getApproach().size() : 0));
            log("   - Performance: " + (view.getPerformanceEvaluation() != null ? view.getPerformanceEvaluation().size() : 0));
            log("   - Component: " + (view.getComponent() != null ? view.getComponent().size() : 0));
            logOptional("   - OS API Functions: ", view.getOSAPISystemFunctions());
            logOptional("   - OS API Functions 2: ", view.getOSAPISystemFunctions2());
            logOptional("   - OS API Functions 3: ", view.getOSAPISystemFunctions3());
            logOptional("   - Systems: ", view.getSystems());
            logOptional("   - Defensive Techniques: ", view.getDefensiveTechnique7());
            logOptional("   - Attack Mitigation: ", view.getAttackEnterpriseMitigation1());
            log("📊 Total items: " + view.getTotalItens());
        }
    }

    private void logOptional(String label, List<?> list) {
        if (list != null && !list.isEmpty()) {
            log(label + list.size());
        } else if (list != null) {
            log(label + "0 (empty)");
        } else {
            log(label + "null");
        }
    }

    private static void log(String msg) {
        System.out.println("[CyberSecViewLoader] " + msg);
    }

    private static void logError(String msg, Exception e) {
        System.err.println("[CyberSecViewLoader] " + msg);
        System.err.println("   📌 Message: " + e.getMessage());
        System.err.println("   📌 Type: " + e.getClass().getSimpleName());
        e.printStackTrace();
    }

    public static boolean isViewLoaded() {
        return viewLoaded;
    }

    public static boolean isViewLoading() {
        return viewLoading;
    }

    public static String getLoaderStatus() {
        return String.format("LoaderStatus{viewLoaded=%s, viewLoading=%s, instance=%s}", 
            viewLoaded, viewLoading, instance != null ? "CREATED" : "NULL");
    }

    public static CyberSecViewLoader getInstance() {
        return instance;
    }
}