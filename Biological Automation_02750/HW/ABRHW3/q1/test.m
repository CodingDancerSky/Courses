function [regrets,modelLosses] = test(n)
clc;
[data,labels] = getDataForTarget1(n);

t = size(data, 2);

eta = 0.5;
regrets = ones(100,100);
modelLosses = ones(100,100);

for i = 1:t
    [regrets(i,:),modelLosses(i,:),~] = hedge(data,labels,eta);
end
size(mean(regrets,1))
figure;
h = plot(1:t, [mean(regrets,1); mean(modelLosses,1)]);
%----------------

n = 100;
[data,labels] = getDataForTarget2(n);

t = size(data, 2);

eta = 0.5;
regrets = ones(100,100);
modelLosses = ones(100,100);

for i = 1:t
    [regrets(i,:),modelLosses(i,:),~] = hedge(data,labels,eta);
end
