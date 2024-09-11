package lazy.demo.image_mngt_file_service.service;

public class Test {
    public static void main(String[] args) {
        long maxHeapSize = Runtime.getRuntime().maxMemory();
        System.out.println("Maximum heap size: " + maxHeapSize / (1024 * 1024) + " MB");
    }
}
