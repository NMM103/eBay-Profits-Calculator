import java.io.*;
public class FileEditor {
	
	private  File file;
	public FileEditor()
	{
		file = null;
	}
	
	public FileEditor(File newFile)
	{
		file = newFile;
	}
	
	public FileEditor(String path, String name)
	{
		newFile(path, name);
	}
	
	public void newFile(String path, String name)
	{
		file = new File(path);
		if (file.exists())
			System.out.println("Success");
		else
		{
			System.out.println("File does not exist\nCreating file at "+path);
			
			if (!name.contains(".txt"))
				name = name+".txt";
			
			try {
				if(file.createNewFile())
					System.out.println("Success");
				else
					System.out.println("File could not be created");
			} catch (IOException e) {

				e.printStackTrace();
			}
		}		
	}
	
	public void setFile(File newFile)
	{
		if(newFile.exists())
			file = newFile;
		else
		{
			System.out.println("File does not exist\n"
					+ "Creating new file");
			newFile(newFile.getPath(), newFile.getName());
		}
	}
	
	
	
	
	public void replaceText(String curr, String replacement)
	{
		String all_text = readAllText();
		if(all_text.contains(curr))
			setAllText(all_text.replace(curr, replacement));
		else
			System.out.println("Did not contain string");
	}
	
	
	
	public void setAllText(String text)
	{
		FileWriter writer;
		try {
			writer = new FileWriter(file);
			writer.write(text);
			writer.close();
		} catch (IOException e) {
			System.out.println(e.toString());
			return;
		}
				
	}
	
	
	
	public void appendText(String text)
	{
		StringBuilder ret = new StringBuilder(readAllText());
		ret.append(text);
		setAllText(ret.toString());
	}
	
	
	
	public String readAllText() {
		String content = "", next = "";
		
		try {
			BufferedReader buf = new BufferedReader(new FileReader(file));
			
			next = buf.readLine();
			while (next != null) 
			{
				content = content + next + System.lineSeparator();
				next = buf.readLine();
			}
			
			buf.close();
			return content;
			
		} catch (Exception e) {
			System.out.println(e.toString() + " in readAllText()");
			e.printStackTrace();
			return null;
		}
		
	}
	
}
