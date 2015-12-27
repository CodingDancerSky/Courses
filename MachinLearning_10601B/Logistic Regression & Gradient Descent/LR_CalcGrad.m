% Calculate the gradient of the logistic regression
% objective function with respect to each parameter

function grad = LR_CalcGrad(XTrain,yTrain,wHat)
  N = size(XTrain,1);
  % TODO: complete the function
  XTrain = [ones(N,1),XTrain];
  grad = XTrain' * yTrain - XTrain' *(ones(N,1) - ones(N,1) ./(ones(N,1) + exp(XTrain * wHat)));

end