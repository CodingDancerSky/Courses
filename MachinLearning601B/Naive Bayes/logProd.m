function [log_product] = logProd(x)
%LOGPROD Given a vector of numbers in log-space
% (i.e. x(i) = log p(i)), this computes the log of the
% product of the numbers: log \product_i p(i).
%
% inputs:
%   x - A vector of length n containing the factors in
%     log-space: x(i) = log p(i).
%
% output:
%   log_product - A scalar containing the log of the
%     product of the numbers: log \product_i p(i).

% TODO: implement me!
log_product = sum(x);
end

