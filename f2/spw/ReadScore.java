package f2.spw;
 
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
 
public class ReadScore{
    String sCurrentLine;
    BufferedReader br = null;
    public String getScore(){
        try {
            br = new BufferedReader(new FileReader("./f2/spw/score.txt"));

            sCurrentLine = br.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if(sCurrentLine != null){
            return sCurrentLine;
        }else
            return null;
    }
    public void writeScore(Long score){

        try {
 
            File file = new File("./f2/spw/score.txt");
 
            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
 
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(Long.toString(score));
            bw.close();
 
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

