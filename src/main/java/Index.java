import java.util.*;

class Index {

  protected static Index box;
//  protected List<PageEntry> listSort = new ArrayList<>();
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

//  public List<PageEntry> getSort(){
//    Collections.sort(listSort);
//    return listSort;
//  }

}
