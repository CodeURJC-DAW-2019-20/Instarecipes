public class Recipe{
    private AtomicInteger id;
    private String username;
    private int id_ingredients;
    private String name_categories;
    private String name_cookingStyle;
    private String title;
    private String description;
    private String duration;
    private String dificulty;
    private String steps;
    private Image galery;


    public AtomicInteger getId() {
		return id;
	}
	public void setId(AtomicInteger id) {
		this.id = id;
    }
    public void setUserName(String user){
        this.username=user;
    }
	public String getUsername() {
		return username;
    }
    public void setidIng(int id_ingredients){
        this.id_ingredients=id_ingredients;
    }
    public int getidIng(){
        return id_ingredients;
    }
    public void getNameCat{
        
    }
     
}