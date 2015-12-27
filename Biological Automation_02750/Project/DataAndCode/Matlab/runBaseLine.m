function [ accuracy ] = runBaseLine(strategy)
%RUNBASELINE Summary of this function goes here
%   Detailed explanation goes here
    NumTrees = 20;
    total = 500;
    NumIteration = 10;
    training_data = [];
    training_labels = [];
    accuracy = zeros(1, total-NumIteration);
    precision = zeros(1, total- NumIteration);
    apc = zeros(1, total - NumIteration);
    recall = zeros(1, total - NumIteration);
    [data_all, label_all] = loadFullData(strategy);
    [full_data, ] =loadFullData(5);
    if(strategy==1)
        cost= 10;
    elseif(strategy==2)
        cost = 250;
    elseif(strategy==3)
        cost = 500;
    elseif(strategy==4)
        cost = 1000;
    else
        cost = 1750;
    end
    for i = 1 : NumIteration
        pid = nextPatient;
        [result,]=orderTest(pid,strategy,full_data);
        l = oracle(pid);
        training_data = [training_data;result];
        training_labels = [training_labels l];
    end
   
    for i = 1 : total - NumIteration
        pid = nextPatient;
        [result,]=orderTest(pid,strategy,full_data);
        l = oracle(pid);
        training_data = [training_data;result];
        training_labels = [training_labels l];
        %B = TreeBagger(NumTrees,training_data,training_labels,'Method','classification');
        SVMModel = fitcsvm(training_data,training_labels,'Cost',[0,1;1,0]);
        p_labels = SVMModel.predict(training_data);
        %test = B.predict(data_all);
        %p_labels = str2double(B.predict(data_all));
        accuracy(i) = computeAccurace(p_labels,training_labels');
        [precision(i),recall(i)] = computePR(p_labels,training_labels');
        apc(i) = accuracy(i)/cost;     
    end
    %SVMModel = fitcsvm(data_all,label_all,'Cost',[0,1;1,0]);
    %p_labels = SVMModel.predict(data_all);
    %accuracy(200) = computeAccurace(p_labels,label_all');
    %[precision(200),recall(200)] = computePR(p_labels,label_all');
    figure(1)
    plot(accuracy,'r')
    hold on
    plot(precision, 'g')
    hold on
    plot(recall, 'b')
    hold on
    plot(apc, 'y')
    hold off
    xlabel('Queries')
    ylabel('results')
    legend('accuracy', 'precision', 'recall','apc')
    title('baseline')
    %SVMModel = fitcsvm(training_data,training_labels);
    %p_labels = SVMModel.predict(data_all);
    %p_labels = str2double(B.predict(data_all));
    
    
end

