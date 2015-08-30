% Calculate the logistic regression objective value

function obj = LR_CalcObj(XTrain,yTrain,wHat)
  N = size(XTrain,1);
  % TODO: complete the function
  XTrain = [ones(N,1),XTrain];
  size(XTrain);
  obj = sum(yTrain .* (XTrain * wHat) - log(1 + exp(XTrain * wHat)));
end