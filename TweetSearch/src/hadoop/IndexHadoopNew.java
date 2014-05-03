package hadoop;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;


/**
 * @author PrajnaS
 */
public class IndexHadoopNew extends Configured implements Tool {
	

    @Override
    public int run(String[] args) throws Exception {
    	Configuration conf = getConf();
        Job job = new Job(conf, "IndexHadoopNew");
 
        job.setJarByClass(IndexHadoopNew.class);
        job.setMapperClass(TweetMapper1New.class);
        job.setReducerClass(TweetReducer1New.class);
 
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        MultipleOutputs.addNamedOutput(job, "text", TextOutputFormat.class,
        		 Text.class, Text.class);
        
        List<String> other_args = new ArrayList<String>();
	      for (int i=0; i < args.length; ++i) {
	        if ("-libjars".equals(args[i])) {
	          DistributedCache.addCacheFile(new Path(args[++i]).toUri(), conf);
	          conf.setBoolean("indexhadoop.libjars", true);
	        } else {
	          other_args.add(args[i]);
	        }
	      }
	
	      FileInputFormat.setInputPaths(job, new Path(other_args.get(0)));
	      FileOutputFormat.setOutputPath(job, new Path(other_args.get(1)));
	      
      
        boolean succ = job.waitForCompletion(true);
        if (! succ) {
          System.out.println("Job1 failed, exiting");
          return -1;
        }
        
        return 0;
        
    }

    public static void main(String[] args) throws Exception {
    	
    	System.out.println("In IndexTweetsHadoop");
    	
    	 long startTime = System.currentTimeMillis();
    	
        int res = ToolRunner.run(new Configuration(), new IndexHadoopNew(), args);
        
        long endTime = System.currentTimeMillis();
        System.out.println("Total Time taken for index creation:" + (endTime-startTime));
        System.exit(res);
    }
}