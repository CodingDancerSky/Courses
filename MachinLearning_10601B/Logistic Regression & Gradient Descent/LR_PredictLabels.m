% Predict the labels for a test set using logistic regression

function [yHat,numErrors] = LR_PredictLabels(XTest,yTest,wHat)

  % TODO: complete the function
  N = size(XTest,1);
  XTest = [ones(N,1),XTest];
  Py0 = ones(N,1) ./(ones(N,1) + exp(XTest * wHat));
  Py1 = 1 - Py0;
 
  numErrors = 0;
  
  for i = 1:N
    if Py1(i) > Py0(i)
        yHat(i) = 1;
    else
        yHat(i) = 0;
    end
    if yTest(i) ~= yHat(i)
        numErrors = numErrors + 1;
    end
  yHat = yHat';
end
