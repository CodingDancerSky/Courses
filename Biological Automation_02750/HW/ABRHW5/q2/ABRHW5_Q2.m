function ABRHW5_Q2

numExp=5;
tloss1=zeros(1,90);
tloss2=zeros(1,90);
for(i=1:numExp)
    display(sprintf('Running round %d out of %d (this may take a few minutes)', i,numExp))
    [data, labels] = getSerumDataRegression;
    display(sprintf(' Mode = 1 ...'))
    [loss1] = RegressionModel(data,labels,1);
    display(sprintf(' Mode = 2 ...'))
    [loss2] = RegressionModel(data,labels,2);
    tloss1 = tloss1+loss1;
    tloss2 = tloss2+loss2;
end
tloss1=tloss1/numExp;
tloss2=tloss2/numExp;

plot(tloss1,'r');
hold on
plot(tloss2,'b');
hold off
xlabel('rounds')
ylabel('mean absolute error');
legend('Top 2','Diverse');

