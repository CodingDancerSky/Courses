function [D] = NB_XGivenY(XTrain, yTrain)
%NP_XGIVENY Estimates the probability that a word is
% observed given (conditioned on) the class label.
%
% inputs:
%   XTrain - An [n x V] matrix where each row describes
%     which words are present in a particular document.
%     XTrain(i, j) is 1 if word j appears in the i-th
%     document.
%   yTrain - An [n x 1] vector containing the class labels
%     for the training documents. yTrain(i) is 1 if the
%     i-th document belongs to The Economist or 2 if it
%     belongs to The Onion.
%
% output:
%   D - A [2 x V] matrix, where D(i, j) is an estimate of
%     the probability that word j in a document with class
%     label i: P[X_j = 1 | Y = i].

V = size(XTrain, 2);

% TODO: implement me!
D = zeros(2, V);

Py =  [sum(2 - yTrain) + 1, sum(yTrain - 1) + 1]; %Y = economist, onion
Pxy1 = (2 - yTrain)' * XTrain + 1;  %P(Xw=1,y=1)
Pxy2 = (yTrain - 1)' * XTrain + 1;  %P(Xw=1,y=2)
D(1,:) = 1/Py(1) * Pxy1;
D(2,:) = 1/Py(2) * Pxy2;

% for the last question
D(1,:) ./ D(2,:)

end

