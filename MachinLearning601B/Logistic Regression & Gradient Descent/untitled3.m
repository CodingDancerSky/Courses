
trainError = [];
testError = [];
trainErrorRate = [];
testErrorRate = [];

k = 10
   while k <= 500;
   subsetInds = randperm(500, k);
   XTrainSubset = XTrain(subsetInds, :);
   yTrainSubset = yTrain(subsetInds);
   [wHat,objVals] = LR_GradientAscent(XTrainSubset,yTrainSubset);
   [yHat,numErrors] = LR_PredictLabels(XTrainSubset,yTrainSubset,wHat)
   trainError = [trainError,numErrors];
   trainErrorRate = [trainErrorRate,(numErrors/k)];
   [yHat,numErrors] = LR_PredictLabels(XTest,yTest,wHat);
   testError = [testError,numErrors];
   testErrorRate = [testErrorRate,(numErrors/200)];
   k = k + 10;
   end
   
   
   
%plot(trainError);
%hold on, plot(testError,'r')
%leg=char('trainError','testError');
%legend(leg);
%hold off;
%plot(trainErrorRate);
%hold on, plot(testErrorRate,'r')
%leg=char('trainErrorRate','testErrorRate');
%legend(leg);
