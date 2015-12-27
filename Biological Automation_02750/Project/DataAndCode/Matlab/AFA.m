function  AFA
%AFA Summary of this function goes here
%   Detailed explanation goes here
    total = 10000;
    NumIteration = 50;
    training_data_1 = [];
    training_data_2 = [];
    training_data_3 = [];
    training_data_4 = []
    training_labels = [];
    accuracy1 = zeros(1, NumIteration);
    precision1 = zeros(1, NumIteration);
    recall1 = zeros(1, NumIteration);
    
    accuracy2 = zeros(1, NumIteration);
    precision2 = zeros(1, NumIteration);
    recall2 = zeros(1, NumIteration);
    [data_all, label_all] = loadFullData(5);
    
    accuracy = zeros(1, total-NumIteration);
    precision = zeros(1, total- NumIteration);
    recall = zeros(1, total- NumIteration);
    apc = zeros(1, total - NumIteration);
    p_labels = [];
    true_labels = [];
    for i = 1 : NumIteration
        pid = nextPatient;
        l = oracle(pid);
        
        [result,]=orderTest(pid,1,data_all);
        training_data_1 = [training_data_1;result];
        
        [result,]=orderTest(pid,2,data_all);
        training_data_2 = [training_data_2;result];
        
        [result,]=orderTest(pid,3,data_all);
        training_data_3 = [training_data_3;result];
        
        [result,]=orderTest(pid,4,data_all);
        training_data_4 = [training_data_4;result];
        
        training_labels = [training_labels l];
    end
    
    model1= fitcsvm(training_data_1,training_labels,'Cost',[0,1;1,0]);
    model2= fitcsvm(training_data_2,training_labels);
    model3= fitcsvm(training_data_3,training_labels);
    model4= fitcsvm(training_data_4,training_labels);
    p_labels_1 = model1.predict(training_data_1);
    p_labels_2 = model2.predict(training_data_2);
    p_labels_3 = model3.predict(training_data_3);
    p_labels_4 = model4.predict(training_data_4);
    
    arr = zeros(1,3);
    arr(1) = computeAccurace(p_labels_2,training_labels')/250;
    arr(2) = computeAccurace(p_labels_3,training_labels')/500;
    arr(3) = computeAccurace(p_labels_4,training_labels')/1000;
    [B,I] = sort(arr,'descend');
    disp(I(1));
    better_model_idx = I(1)+1;
    if I(1) ==1
        better_model = model2;
    elseif I(1) == 2
        better_model = model3;
    else
        better_model = model4;
    end
    [precision1(1),recall1(1)] = computePR(p_labels_1,training_labels');
    accuracy2(1) = computeAccurace(p_labels_2,training_labels');
    [precision2(1),recall2(1)] = computePR(p_labels_2,training_labels');
    
    % determine for which instance we need request test 2
    test1_wrong = abs(p_labels_1 - training_labels');
    B = TreeBagger(20,training_data_1, test1_wrong);
    request2 = 0;
    true = 0;
    cost = 0;
    
    for i = 1 : (total-NumIteration)
        disp(i);
        disp(request2);
        cost = cost + 10;
        pid = nextPatient;
        [t1_result,]=orderTest(pid,1,data_all);
        if str2double(B.predict(t1_result))==1
            [t2_result,]=orderTest(pid,better_model_idx,data_all);
            request2 = request2 + 1;
            l = better_model.predict(t2_result);
            cost = cost + 250;
        else
            l = model1.predict(t1_result);
        end
        if(l==label_all(pid))
            true = true +1;
        end
        p_labels = [p_labels l];
        true_labels = [true_labels label_all(pid)];
        disp(true/i);
        accuracy(i) = true/i;
        [precision(i),recall(i)] = computePR(p_labels, true_labels);
        apc(i) = accuracy(i)/cost; 

    end
    disp('total cost');
    disp(cost);
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

end

