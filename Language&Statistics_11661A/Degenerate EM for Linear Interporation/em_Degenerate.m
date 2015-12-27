function [t, Lambda,L,ratio] = em_Degenerate(Prob)
    n = size(Prob,1);
    Lambda = [];
    L = [];
    ratio = [];
    oldLambda = [0.1,0.45,0.45];
    oldL = sum(log(Prob * oldLambda')) / n;
    k = size(oldLambda,2);
    t = 0;
    eta = 0.00001;

    hasConverged = false;
    while hasConverged == false
        Lambda = [Lambda;oldLambda];
        L = [L;oldL];
        %newLambda = ((oldLambda * Prob) / sum(newLambda * Prob)) / n;
        newLambda = oldLambda;
        for i = 1:k
            %size((newLambda(i) * Prob(:,i)))
            %size((Prob * newLambda'))
            newLambda(i) = sum((newLambda(i) * Prob(:,i)) ./ (Prob * newLambda')) / n;
        end
            
        newL = sum(log(Prob * newLambda')) / n;
        [hasConverged,ratio]  = checkConvg(oldL,newL,eta,ratio);
        oldLambda = newLambda;
        oldL = newL;
        t = t + 1;
    end
    
    Lambda = [Lambda;oldLambda];
    L = [L;oldL];
    
    [X,Y] = meshgrid(Lambda);
    Z=1-X-Y;
    surf(X,Y,Z);
    
end