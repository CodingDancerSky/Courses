[wHat,objVals] = LR_GradientAscent(XTrain,yTrain); 

j=find(wHat==max(wHat))
wHat(j)=min(wHat);
k=find(wHat==max(wHat))
figure;
hold on;
 for i = 1:200
     if yTest(i) == 0
        plot(XTest(i,j),XTest(i,k),'or');
     else plot(XTest(i,j),XTest(i,k),'xb');
     end
 end
 
 x = -4:4
 y = - (wHat(k)/wHat(j))*x;
 plot(x,y,'k');
 hold off;