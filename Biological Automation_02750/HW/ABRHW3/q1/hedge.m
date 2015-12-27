function [regrets,modelLosses,weights] = hedge(data,labels,eta)

%% Simulate the Hedge Algorithm
% Inputs: data - an n by t matrix where n is the number of experts and t is the number of iterations of the algorithm
%         labels - a 1 by t vector containing the true labels for each of the t iteractions
%         eta - the learning rate, which is a value in (0,1)
% Outputs: regrets - a 1 by t vector containing the INSTANTANEOUS regrets for iterations 1 to t
%          modelLosses - a 1 by t vector containing the INSTANTANEOUS loss at iteration t
%          weights - a 1 by n vector containing the FINAL weights over the experts

[n,t] = size(data);
weights = ones(1,n);
regrets = zeros(1,t);
modelLosses = zeros(1,t);
alternative = zeros(1,t);

for(i=1:t) 
    % modelIndex = find(mnrnd(1, weights / sum(weights))~=0);
    modelIndex = find(mnrnd(1, weights / sum(weights))~=0);
    alternative(i) = data(modelIndex, i);
    [regret, modelLoss] = computeRegret(alternative(1:i), data(:,1:i), labels(1:i));
    regrets(i) = regret;
    modelLosses(i) = modelLoss;
    
    for j= 1:n
     weights(j) = weights(j) * eta * exp(-1 * abs(data(j,i) - labels(i)));

    end
end
weights=weights/sum(weights); % re-normalize weights
end