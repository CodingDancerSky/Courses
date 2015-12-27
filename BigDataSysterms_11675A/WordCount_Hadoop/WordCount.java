package wordcount;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class WordCount {
    public static class TokenCounterMapper extends Mapper<Object, Text, Text, IntWritable>{
        private final static IntWritable one = new IntWritable(1); // Integer that can be printed out
        private Text word = new Text();
        
        //One file corresponds to one map
        //For every pair of input <K-V>, implement StringTokenizer to get the token of the value
        //and then for each token, distribute one pair of <token, one>, which is collected by reducer.
        //the same <K-V> with the same token will be collected in the same reducer
        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            //Pre-processing the word(ignore the punctuations)
            StringTokenizer itr = new StringTokenizer(value.toString().replaceAll("[^A-Za-z]+", " "));
            while (itr.hasMoreTokens()) {
                word.set(itr.nextToken());
                context.write(word, one);
            }
        }
    }
    
    //All maps corresponds to the same reducer
    //Count
    public static class IntSumReducer extends Reducer<Text,IntWritable,Text,IntWritable> {
        private IntWritable result = new IntWritable();
        
        public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable v: values) {
                sum += v.get();
            }
            result.set(sum);
            context.write(key, result);
        }
    }
    
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        
        //must have input and output
        if(args.length != 2){
            System.err.println("Usage: wordcount <in> <out>");
            System.exit(2);
        }
        
        Job job = Job.getInstance(conf, "word count");
        job.setJarByClass(WordCount.class);// main class
        job.setMapperClass(TokenCounterMapper.class); //mapper
        job.setCombinerClass(IntSumReducer.class);
        job.setReducerClass(IntSumReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
