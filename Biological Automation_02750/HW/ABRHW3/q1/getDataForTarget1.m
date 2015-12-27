function [data,labels] = getDataForTarget1(n)

data = randn(n,100);
labels = data(1,:) + randn(1,100) * .1;

end