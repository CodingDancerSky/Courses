function [weights,errors] = winnow(data, labels)

%% Simulate the Winnow algorithm
% Inputs: data - an n by m matrix where n is the number of instances and m is the number of features
%         labels - a 1 by n vector containing the labels for the instances.
% Output: weights - a 1 by m vector of weights for each feature. large values mean more relevant
%         errors - a scalar indicating the total number of errors made by the classifier

[nrow, ncol] = size(data);
weights = ones(1,ncol);
theta = ncol / 2; % this is the decision threshold.
errors = 0;

    %% IMPLEMENT THE WINNOW ALGORITHM HERE
for i = 1:nrow
    train = data(i,:);
    y_pred = train * weights';
    
    if(y_pred > theta)
        label_pred = 1;
    else 
        label_pred = 0;
    end
    if(label_pred ~= labels(i))
        errors = errors + 1;
        
        if(label_pred == 1 && labels(i) == 0)
            for j = 1:ncol
                if(data(i,j) == 1)
                    weights(j) = weights(j) / 2;
                else
                    weights(j) = weights(j) * 2;
                end
            end
        end
    
        if(label_pred == 0 && labels(i) == 1)
            for j = 1:ncol
                if(data(i,j) == 0)
                    weights(j) = weights(j) / 2;
                else
                    weights(j) = weights(j) * 2;
                end
            end
        end
    end
end

end