function [loss] = RegressionModel(data,labels,mode)
% Implements a regression model
% Inputs: data - a 1000 by 25 matrix containing the data from 1000 patients.
%         labels - a 1 by 1000 vector containting the survival times for the patients
%         mode - a flag that determines which batch selection strategy is used. 1 = default selection mode; 2 = diverse label mode
% Outputs: loss a 1 by 90 vector that returns the generalization error as function of the number of rounds (90) 

loss = zeros(1,90);

if(mode == 1)
    index = randsample(size(data,1), 20, true)';
    data_training = data(index,:);
    labels_training = labels(index);
    
    for(k = 1:90)
        mdl = fitlm(data_training, labels_training);
        [ypred,yci] = predict(mdl,data);
        ypred = abs(ypred);
        loss(k) = sum(abs(labels - ypred')/1000);
        
        centre = mean(data_training);
        restdata = removerows(data,'ind',index);
        restlabel = labels;
        restlabel(index) = [];
        
        distances = pdist2(centre, restdata, 'euclidean');
        [val, idx] = sort(distances);
        data_training = [data_training; restdata(idx(size(idx,2)),:);restdata(idx(size(idx,2)-1),:)];
        labels_training = [labels_training, restlabel(idx(1)), restlabel(idx(2))];
    end
end

if(mode == 2)
    index = randsample(size(data,1), 20, true)';
    data_training = data(index,:);
    labels_training = labels(index);
    
    for(k = 1:90)
        mdl = fitlm(data_training, labels_training);
        [ypred,yci] = predict(mdl,data);
        ypred = abs(ypred);
%         bag = fitensemble(data_training,labels_training,'Bag',400,'Tree','type','classification');
%         [ypred scores] = bag.predict(data);
        loss(k) = sum(abs(labels - ypred')/1000);

        centre = mean(data_training);
        restdata = removerows(data,'ind',index);
        restlabel = labels;
        restlabel(index) = [];
        
        distances = pdist2(centre, restdata, 'euclidean');
        [val, idx] = sort(distances);
        y_pred_rest = ypred(idx(1:20));
        [val_y, idx_y]=sort(y_pred_rest);
        data_training = [data_training; restdata(idx_y(1),:);restdata(idx_y(size(idx,1)),:)];
        labels_training = [labels_training, restlabel(idx_y(1)), restlabel(idx_y(size(idx,1)))];
    end
end

end