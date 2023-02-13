
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.io.IOException;
import java.util.*;


public class BooleanSearchEngine implements SearchEngine {
  protected Map<String, List<PageEntry>> wordMap;

  public BooleanSearchEngine(File pdfsDir) throws IOException {
    wordMap = Index.getIndex().getIndexMap();
    File[] files = pdfsDir.listFiles();

    if (files != null && files.length > 0) {
      for (File file : files) {
        if (file.getName().contains(".pdf")) {
          PdfDocument doc = new PdfDocument(new PdfReader(file));

          for (int i = 0; i < doc.getNumberOfPages(); i++) {
            PdfPage page = doc.getPage(i + 1);
            var text = PdfTextExtractor.getTextFromPage(page);
            var words = text.split("\\P{IsAlphabetic}+");

            Map<String, Integer> freqs = new HashMap<>();
            for (var word : words) {
              if (word.isEmpty()) {
                continue;
              }
              int newFreqs = freqs.getOrDefault(word, 0) + 1;
              freqs.put(word.toLowerCase(), newFreqs);
            }
            String titlePDF = doc.getDocumentInfo().getTitle();
            for (Map.Entry<String, Integer> entry : freqs.entrySet()) {

              String tmpWord = entry.getKey();
              int tmpValue = entry.getValue();

              List<PageEntry> tmpPage = new ArrayList<>();
              tmpPage.add(new PageEntry(titlePDF, i + 1, tmpValue));

              if (wordMap.containsKey(tmpWord)) {
                wordMap.get(tmpWord).add(new PageEntry(titlePDF, i + 1, tmpValue));
              } else {
                wordMap.put(tmpWord, tmpPage);
              }
            }
          }
        }
      }
      wordMap.values().forEach(Collections::sort);
    }
  }

  @Override
  public List<PageEntry> search(String word) {
    String lowRegisterWord = word.toLowerCase();
    List<PageEntry> resultList = wordMap.getOrDefault(lowRegisterWord, Collections.emptyList());
    return resultList;
  }
}