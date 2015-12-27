function ABRHW5_Q1(part)
%% Automation of Biological Research Homework number 5, question 1, Fall 2015
% Authors:  Christopher James Langmead;
% Version: 0.1
% Date: 10/29/2015
% Description
% This file contains the code for running parts d,e, and f of the 1st
% question

if(strcmp(part,'d')==1)
    display('Running part d (this may take a few minutes) ...')
    [data, labels, T] = getSerumData(); % returns data matrix, labels vector, and the hierarchical tree; for more details, please refer to http://www.mathworks.com/help/stats/linkage.html#zmw57dd0e352239
    loss_avg = zeros(1, size(data,1)); % initialize the loss vector (one element per data point)
    numtrials = 5; % run 5 times
    for(i =1:numtrials)
    display(sprintf('  Running trial %d',i))
        [L,loss] = DH_SelectCase1(data, labels, T); % run the DH algorithm; L is the vector of predictions; loss is the loss curve over the iterations
        loss_avg = loss_avg + loss; % add the loss curves
    end
    loss_avg = loss_avg/numtrials; % compute average loss curve
    figure(1)
    plot(loss_avg/1000)
    xlabel('Queries')
    ylabel('Generalization Error')
    title('Part D: Serum Data')
elseif(strcmp(part,'e')==1)
    display('Running part e (this may take a few minutes) ...')
    [data, labels, T] = getUrineData(); % returns data matrix, labels vector, and the hierarchical tree; for more details, please refer to http://www.mathworks.com/help/stats/linkage.html#zmw57dd0e352239
    loss_avg = zeros(1, size(data,1)); % initialize the loss vector (one element per data point)
    numtrials = 5; % run 5 times
    for(i =1:numtrials)
    display(sprintf('  Running trial %d',i))
        [L,loss] = DH_SelectCase1(data, labels, T); % run the DH algorithm; L is the vector of predictions; loss is the loss curve over the iterations
        loss_avg = loss_avg + loss; % add the loss curves
    end
    loss_avg = loss_avg/numtrials; % compute average loss curve
    figure(2)
    plot(loss_avg/1000)
    xlabel('Queries')
    ylabel('Generalization Error')
    title('Part E: Urine Data')
    
elseif(strcmp(part,'f')==1)
    display('Running part f (this may take a few minutes) ...')
    [data, labels, T] = getSerumData(); % returns data matrix, labels vector, and the hierarchical tree; for more details, please refer to http://www.mathworks.com/help/stats/linkage.html#zmw57dd0e352239
    loss_avg = zeros(1, size(data,1)); % initialize the loss vector (one element per data point)
    numtrials = 5; % run 5 times
    for(i =1:numtrials)
    display(sprintf('  Running trial %d',i))
        [L,loss] = DH_SelectCase2(data, labels, T); % run the DH algorithm; L is the vector of predictions; loss is the loss curve over the iterations
        loss_avg = loss_avg + loss; % add the loss curves
    end
    loss_avg = loss_avg/numtrials; % compute average loss curve
    figure(3)
    plot(loss_avg/1000)
    xlabel('Queries')
    ylabel('Generalization Error')
    title('Part F: Serum Data')
elseif(strcmp(part,'g')==1)
    [data, labels, T] = getUrineData(); % returns data matrix, labels vector, and the hierarchical tree; for more details, please refer to http://www.mathworks.com/help/stats/linkage.html#zmw57dd0e352239
    loss_avg = zeros(1, size(data,1)); % initialize the loss vector (one element per data point)
    numtrials = 5; % run 5 times
    for(i =1:numtrials)
        [L,loss] = DH_SelectCase2(data, labels, T); % run the DH algorithm; L is the vector of predictions; loss is the loss curve over the iterations
        loss_avg = loss_avg + loss; % add the loss curves
    end
    loss_avg = loss_avg/numtrials; % compute average loss curve
    figure(4)
    plot(loss_avg/1000)
    xlabel('Queries')
    ylabel('Generalization Error')
    title('Part G: Urine Data')
else
    display('Error: part should be d, e, or f')
    
end
end