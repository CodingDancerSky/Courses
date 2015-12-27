package knn;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class KNN_Driver extends Configured implements Tool {
  public static void main(String[] args) throws Exception {
    int res = ToolRunner.run(new Configuration(), new KNN_Driver(), args);
    System.exit(res);
  }

  @Override
  public int run(String[] args) throws Exception {
    // config a job and start it
    Configuration cf = getConf();
    cf.set("k", args[2]);
        
      Job job = Job.getInstance(cf, "knn");
      job.setJarByClass(KNN_Driver.class);
      job.setMapperClass(KNN_Mapper.class);
      job.setCombinerClass(KNN_Combiner.class);
      job.setReducerClass(KNN_Reducer.class);

      job.setOutputKeyClass(Text.class);
//      job.setMapOutputValueClass(KNN_PointDistance.class);
      job.setOutputValueClass(Text.class);

      FileInputFormat.addInputPath(job, new Path(args[0]));
      Path out = new Path(args[1]);
//      FileSystem.get(cf).delete(out, true);
      FileOutputFormat.setOutputPath(job, out);
      //set the current testing file
      int res = job.waitForCompletion(true) ? 0 : 1;
      if (res != 0) {
          return res;
      }
    return 0;
  } 
}
