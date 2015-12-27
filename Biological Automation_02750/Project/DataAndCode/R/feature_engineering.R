data <- read.csv("../stream.csv", stringsAsFactors = FALSE)
head(data)
dim(data)
training <- data[1:200,]
  
data1 <-as.matrix(training[,1:3])
data2 <-as.matrix(training[,4:28])
data3 <-training[,29:128]
data4 <-training[,129:130]

heatmap(data1)

// test 1
require(scatterplot3d)
scatterplot3d(data1[,1], data1[,2], data1[,3], 
              xlab = "feature1", ylab = "feature2", zlab = "feature3",
              highlight.3d = TRUE)


// test 2
heatmap(data2)
plot(colMeans(data2))
plot(rowMeans(data2))

// test 3
heatmap(data3)
plot(colMeans(data3))
plot(rowMeans(data3))


// test 4
heatmap(data4)
table(colMeans(data4))
plot(rowMeans(data4))