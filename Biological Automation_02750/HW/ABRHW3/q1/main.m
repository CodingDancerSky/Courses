clc;

n = 100;
[regrets10,modelLosses10] = test(10);
[regrets100,modelLosses100] = test(100);

figure;
h = plot(1:t, [mean(regrets10,1); mean(modelLosses10,1); mean(regrets100,1); mean(modelLosses100,1)]);
legend(h, 'regret\_10', 'model loss\_10', 'regret\_100', 'model loss\_100');