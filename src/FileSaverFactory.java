public class FileSaverFactory {
    public FileSaver createHTMLFileSaver(String filePath) {
        return new HTMLFileSaver(filePath);
    }
}
