package composite;

public class FileSystem {
	public void main(String[] args) throws Exception {
		Folder root = new Folder("root");
		Folder images = new Folder("images");
		File img1 = new File("logo.png");
		File doc1 = new File("readme.txt");
		images.addFile(img1);
		root.addFile(images);
		root.addFile(doc1);
		root.draw("");
	}
}
