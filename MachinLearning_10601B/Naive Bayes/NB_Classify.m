function [yHat] = NB_Classify(D, p, XTest)
%NP_CLASSIFY Predicts the class labels for some given
% test input.
%
% inputs:
%   D - A [2 x V] matrix, where D(i, j) is an estimate of
%     the probability that word j in a document with class
%     label i (the output of NB_XGivenY).
%   p - A scalar characterizing the prior probability that
%     the class label is 1 (the output of NB_YPrior).
%   XTest - An [m x V] matrix where each row describes
%     which words are present in a particular *test*
%     document. XTest(i, j) is 1 if word j appears in the
%     i-th document.
%
% output:
%   yHat - An [m x 1] vector of predicted class labels,
%     where yHat(i) is the predicted class label for the
%     i-th document (the i-th row of XTest).

m = size(XTest, 1);

% TODO: implement me!
yHat = zeros(m, 1);

logPxx1 = log(1 - D(1,:) + eps) * (1 - XTest') + log(D(1,:) + eps) * XTest' + log(p + eps);
logPxx2 = log(1 - D(2,:) + eps) * (1 - XTest') + log(D(2,:) + eps) * XTest' + log(1 - p + eps);

for i = 1:m
    if logPxx1(i) > logPxx2(i)
        yHat(i) = 1;
    else
        yHat(i) = 2;
    end
  %yHat(m) = if(logPxx1' > logPxx2')?1: 2;

end

