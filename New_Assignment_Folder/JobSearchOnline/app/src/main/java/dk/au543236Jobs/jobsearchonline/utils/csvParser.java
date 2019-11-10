package dk.au543236Jobs.jobsearchonline.utils;

/*
public class csvParser
{
    private InputStream inputStream;
    public  csvParser(InputStream inputStream) { this.inputStream = inputStream; }

    public List getAssets()
    {
        List list = new ArrayList<Job>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                String[] row = line.split(";");
                list.add(new Job(row[0], row[1], row[2], row[3]));
            }
        }
        catch (IOException ex)
        {
            throw new RuntimeException("Error: Could not read csv file.");
        }
        finally
        {
            try
            {
                inputStream.close();
            }
            catch (IOException ex)
            {
                throw new RuntimeException("Error: Could not close input stream.");
            }
        }
        return list;
    }
}
*/