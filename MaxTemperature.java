import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.conf.Configuration;

public class MaxTemperature {
    public static void main(String[] args) throws Exception {
       /* if (args.length != 2) {
            System.err.println("Usage: MaxTemperature <input path> <output path>");
            System.exit(-1);
        }*/
        // Configuration conf = new Configuration();
        // A Job object forms the specification of the job and gives you control over how the job is run.
        Job job = new Job();

        // When we run this job on a Hadoop cluster, we will package into a JAR file
        job.setJarByClass(MaxTemperature.class);
        job.setJobName("Max temperature");

        // inputPath can be a single file or a directory. We can add multiples input path.
        FileInputFormat.addInputPath(job, new Path(args[1]));

        // outputPath is only one. It specifies a directory where the output files from reduce function are written.
        // IMPORTANT!: the directory shouldn't exist before running the job. This precaution is to prevent data loss
        FileOutputFormat.setOutputPath(job, new Path(args[2]));

        // We specify the mapreduce's files
        job.setMapperClass(MaxTemperatureMapper.class);
        job.setReducerClass(MaxTemperatureReducer.class);

        // output type class and must math what the reducer class producer
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        // The waitForCompletion() method on Job submits the job and wait  for it to finish
        // When true, the job writes information about its progress to the console.
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
