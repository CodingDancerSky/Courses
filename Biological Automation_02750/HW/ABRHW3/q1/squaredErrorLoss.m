function loss = squaredErrorLoss(predictions, labels)
%% compute cummulative squared error loss
% Inputs: predictions - this is a 1 by t vector of predictions up to iteration t
%         labels - this is a 1 by t vector of true labels up to iteration t
%
% Output loss - this is the squared error loss
    
    
    loss = sum((predictions - labels).^2);
end