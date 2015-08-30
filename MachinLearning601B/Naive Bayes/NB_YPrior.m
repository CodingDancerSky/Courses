function [p] = NB_YPrior(yTrain)
%NP_YPRIOR Computes the prior probability that the class
% label is 1: P[Y = 1].
%
% inputs:
%   yTrain - An [n x 1] vector containing the class labels
%     for the training documents. yTrain(i) is 1 if the
%     i-th document belongs to The Economist or 2 if it
%     belongs to The Onion.
%
% output:
%   p - The prior probability that the class label is 1.

% TODO: implement me!
p = sum(2 - yTrain) / size(yTrain, 1); 

end

