% Run the gradient ascent algorithm for logistic regression

function [wHat,objVals] = LR_GradientAscent(XTrain,yTrain)

    % Set the step size
    eta = .01;
    
    % Set the convergence tolerance
    tol = .001;
    wHat = zeros(11,1);
    % TODO: implement the remainder of the algorithm
    oldObj = LR_CalcObj(XTrain,yTrain,wHat);
    hasConverged = false;
    objVals = [];
    while hasConverged == false
        objVals = [objVals, oldObj];
        grad = LR_CalcGrad(XTrain,yTrain,wHat);
        wHat = LR_UpdateParams(wHat,grad,eta);
        newObj = LR_CalcObj(XTrain,yTrain,wHat);
        hasConverged = LR_CheckConvg(oldObj,newObj,tol);
        oldObj = newObj;
    end
        objVals = [objVals, oldObj];
        
  
   
   %plot(objVals);
   %title('Converges')
   %xlabel('iterations: t')
   %ylabel('objective function l(w)')
   
   
end