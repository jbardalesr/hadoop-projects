import java.io.IOException;

// Hadoop provides its own set of basic types that are optimized for network serialization
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MaxTemperatureMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    private static final int MISSING = 9999;

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // the map is the year in the record
        String line = value.toString();
        // we extract the column for the map
        String year = line.substring(15, 19);
        // and its value
        int airTemperature;
        if (line.charAt(87) == '+') {// parseInt doesn't like leading plus sign
            airTemperature = Integer.parseInt(line.substring(88, 92));
        } else {
            airTemperature = Integer.parseInt(line.substring(87, 92));
        }
        String quality = line.substring(92, 93);

        if (airTemperature != MISSING && quality.matches("[01459]")) {
            // map provides an Instance of Context to write the output, we use Text for write a text and IntWritable for write a number
            context.write(new Text(year), new IntWritable(airTemperature));
        }
    }
}
