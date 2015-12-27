function [data,labels] = getDataForTarget2(n)

data = randn(n,100);
labels = data(10,:)+randn(1,100)*1;

end