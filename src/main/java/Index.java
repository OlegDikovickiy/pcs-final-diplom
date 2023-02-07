import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Index {

  protected static Index box;
  protected Map<String, List<PageEntry>> indexMap = new HashMap<>();

  private Index() {
  }

  public static synchronized Index getIndex() {
    if (box == null) {
      box = new Index();
    }
    return box;
  }

  public Map<String, List<PageEntry>> getIndexMap() {
    return indexMap;
  }

}
