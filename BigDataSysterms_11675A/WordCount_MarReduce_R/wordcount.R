require('rmr2')
wordcount = function(input, output, pattern="\\s+"){
  #mapper:
  wc.map = function(., lines) {
   keyval(
     unlist(
       strsplit(
         x = lines, split = pattern)),
     1) 
  }
  
  #reducer
  wc.reduce = function(word, counts){
    keyval(word, sum(counts))
  }
  
  #mapreduce
  mapreduce(
    input = input,
    output = output,
    input.format = "text",
    output.format = "text",
    map = wc.map,
    reduce = wc.reduce)
}

ret = from.dfs(out)
ret.df = as.data.frame(ret, stringAsFactors = T)
View(ret.df)