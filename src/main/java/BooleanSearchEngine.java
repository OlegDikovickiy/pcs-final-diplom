import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class BooleanSearchEngine implements SearchEngine {
  protected final Map<PageEntry, String> wordMap = new HashMap<>();

  public BooleanSearchEngine(File pdfsDir) throws IOException {
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
              wordMap.put(new PageEntry(file.getName(), doc.getPageNumber(page), newFreqs), word.toLowerCase());
            }
          }
          doc.close();
        }
      }
    }
  }

  @Override
  public List<PageEntry> search(String word) {
    List<PageEntry> resultList = new ArrayList<>();

    for (Map.Entry<PageEntry, String> entry : wordMap.entrySet()) {
      if (entry.getValue().equals(word)) {
        resultList.add(entry.getKey());
      }
    }
    Collections.sort(resultList);
    return resultList;
  }

}