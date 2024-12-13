package lazy.demo.image_mngt_file_service.service;

public class Test {
    public static void main(String[] args) {
        long maxHeap = Runtime.getRuntime().maxMemory();
        long totalHeap = Runtime.getRuntime().totalMemory();
        long freeHeap = Runtime.getRuntime().freeMemory();

        System.out.println("Max Heap (MB): " + maxHeap / 1024 / 1024);
        System.out.println("Total Heap (MB): " + totalHeap / 1024 / 1024);
        System.out.println("Free Heap (MB): " + freeHeap / 1024 / 1024);
    }
}
