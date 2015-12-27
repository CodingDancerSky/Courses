numOfImage = 3;

%Computing Eigen faces
trainFile = dir('input/*.pgm');
trainNum = length(trainFile);
trainImage = cell(trainNum , 1);

image = double(imread('input/Aaron_Eckhart_0001.pgm'));
[nrows, ncolumns] = size(image);
imageMatrix = zeros(nrows*nrows, ncolumns);

for i = 1: trainNum
    path = strcat('input/', trainFile(i,1).name);
    image = double(imread(path));
    imageMatrix(:,i) = image(:);
end

%Computing eigen faces
corrmatrix = imageMatrix * imageMatrix';
[eigvecs, eigvals] = eig(corrmatrix);
%plot(diag(eigvals));
eigenfaceimage = cell(numOfImage, 1);

for i = 1: numOfImage
    eigenfacevector = eigvecs(:,i);
    eigenfaceimage{i} = reshape(eigenfacevector, nrows, ncolumns);
end

%Find the face expresion
faceFile = dir('BoostingData/train/face/*.pgm');
numOfFace = length(faceFile);
faceImage = cell(numOfFace, 1);
eigenExpression = [];
for n = 1 : numOfFace
    image = imread(strcat('BoostingData/train/face/', faceFile(n).name));
    face = double(squeeze(mean(image)));
    faceImage{n} = face;
    [nrow, ncol] = size(face);
    scaledFace = face(:);
    
    %Compute the eigen weight
    w = cell(numOfImage, 1);
    eigenMatrix = [];
    for j = 1 : numOfImage
        y = eigenfaceimage{j};
        y = imresize(y, [nrow, ncol]);
        y = y(:);
        eigenMatrix = [eigenMatrix y];
        w{j} = pinv(y) * scaledFace;
        
        scaledFace = scaledFace - w{j} * y;
        
    end
    eigenExpression = [eigenExpression w(:)];
end

faceExpression = cell2mat(eigenExpression);


%Find the non-face expresion
nonfaceFile = dir('BoostingData/train/non-face/*.pgm');
numOfnonFace = length(nonfaceFile);
nonfaceImage = cell(numOfnonFace, 1);
eigenExpression2 = [];
for n = 1 : numOfnonFace
    image2 = imread(strcat('BoostingData/train/non-face/', nonfaceFile(n).name));
    nonface = double(squeeze(mean(image2)));
    nonfaceImage{n} = nonface;
    [nrow2, ncol2] = size(nonface);
    scalednonFace = nonface(:);
    
    %Compute the eigen weight
    w2 = cell(numOfImage, 1);
    eigenMatrix2 = [];
    for j = 1 : numOfImage
        y2 = eigenfaceimage{j};
        y2 = imresize(y2, [nrow2, ncol2]);
        y2 = y2(:);
        eigenMatrix2 = [eigenMatrix2 y2];
        w2{j} = pinv(y2) * scalednonFace;
        
        scalednonFace = scalednonFace - w2{j} * y2;
        
    end
    eigenExpression2 = [eigenExpression2 w2(:)];
end

nonfaceExpression = cell2mat(eigenExpression2);

% Train the adaboost model

% Set the data class 
total_num = total_num;
trueLabel = [ones(1, numOfFace), ones(1, numOfnonFace).*(-1)];
updateResult = ones(1, total_num);
% Weight of training samples, first every sample is even important

D = ones(1, total_num)./total_num;
weightOfEigen = [faceExpression, nonfaceExpression];
itt = 20;
model = struct;

for round = 1 : itt
    rResult = [];
    sign = [];
    setOfMinErr = zeros(1, 10);
    setOfMinThreshold = zeros(1, 10);
    positionOfCut = zeros(1, numOfImage);
    for ith = 1 : numOfImage
        ithWeight = weightOfEigen(ith, :);
        minithWeight = min(ithWeight);
        boundary = (max(ithWeight) - min(ithWeight)) / 50;
        err = zeros(1, 50);
        
        % find the error weight of each cut in the current ith
        for cut = 1 : 50
            tempOfThreshold = minithWeight + boundary * cut;
            tempLabel = ones(1, total_num);
            tError = 0;
            % check if predict right
            for curr = 1 : (total_num)
                if ithWeight(1, curr) < tempOfThreshold
                    tempLabel(1, curr) = -1;
                end
            end
            
            if tError > 0.5
                tempLabel = -tempLabel;
                tError = 1 - tError;
                sign = [sign -1];
            else
                sign = [sign 1];
            end
            % store the predict label for every loop
            rResult = [rResult; tempLabel];
            
            err(1, cut) = tError;
        end
        [setOfMinErr(1, ith),minOfErr] = min(err);
        setOfMinThreshold(1, ith) = minithWeight + boundary * minOfErr;
        positionOfCut(1, ith) = minOfErr;
    end
    err = min(setOfMinErr);
    indexOfMin = find(setOfMinErr == err);
    resultOfThreshold = setOfMinThreshold(1, indexOfMin);
    updateResult = rResult(indexOfMin * positionOfCut(indexOfMin), :);
    
    %update D so that wrongly classified samples will have more weight
    alpha = 1/2 * log((1 - err) / max(err, eps));
    D = D.* exp(sign * alpha);
    D = D ./ sum(D);
    
    % update the model of H
    model(t).alpha = alpha;
    model(t).resultOfThreshold = resultOfThreshold;
    model(t).indexOfMin = indexOfMin;
    model(t).direction = sign(1, indexOfMin * positionOfCut(indexOfMin));
    
end


