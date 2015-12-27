%Read in the test image
testFile = dir('MLSP_Images/*.pgm');
testNum = length(testFile);
testImage = cell(testNum, 1);
for n = 1 : testNum
    image = imread(strcat('MLSP_Images/', testFile(n).name));
    test = double(squeeze(mean(image)));
    testImage{n} = test;
end


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

eigenfacevector = eigvecs(:,1);
eigenfaceimage = reshape(eigenfacevector, nrows, ncolumns);
imagesc(eigenfaceimage)

%Scanning an image for a pattern
I = imread('MLSP_Images/Image1.jpg');

%eigvecs = eigvecs/norm(eigvecs(:)); 
%m = zeros(P-N, Q-M);
%for i = 1:(P-N)
%    for j = 1:(Q-M)
%        patch = I(i:i+N-1,j:j+M-1);
%        m(i,j) = eigvecs(:)'*patch(:) / norm(patch(:)); 
%    end
%end

scaledimage = cell(5, 1);
%Converting color to greyscale:
greyscaleimage = squeeze(mean(I,3));
%Histogram equalization
imageCurr = double(greyscaleimage - mean(greyscaleimage(:)));
[P, Q] = size(image);
%Scaling images
scale =0.5;
for i = 1:5
    scaledimage{i} = imresize(imageCurr,[P,Q]);
end

[N, M] = size(eigenfaceimage);
for k = 1: 5
    A = scaledimage{k};
    E = eigenfaceimage;
    pixelsinpatch = N * M;
    %first compute the integral image
    integralA = cumsum(cumsum(A,1),2);

    %Now, at each pixel compute the mean
    patchmeansofA = zeros(size(A));
    for i = 1: size(A,1)- N + 1
        for j = 1: size(A,2) - M + 1
            a1 = integralA(i,j);
            a2 = integralA(i + N - 1,j);
            a3 = integralA(i,j + M - 1);
            a4 = integralA(i + N - 1,j + M - 1); 
            patchmeansofA(i,j) = a4 + a1 - a2 - a3;
        end
    end

    tmpim = conv2(A, fliplr(flipud(E)));
    convolvedimage = tmpim(N:end, M:end);
    
    [r, c] = size(convolvedimage);
    scaledpatchmeansofA = imresize(patchmeansofA, [r, c]);
    
    sumE = sum(E(:));
    patchscore{k} = convolvedimage - sumE * scaledpatchmeansofA(1:r, 1:c);
    
    I = patchscore{k};
    imagesc(int8(A .* (I > prctile(I(:), 80))));
%     shapeInserter = vision.ShapeInserter;
%     shapeInserter.LineWidth = 5;
%     for i = 1:5
%         h1 = int32(['input/Aaron_Eckhart_0001.pgm' 886-location(i,2) 100 100]);
%         I =step(shapeInserter, I, h1);
%         imshow(I);
%     end
%     imwrite(I, 'EigenFace1st.pgm', 'pgm');

%     faceSpot = cell(5, 1);
%     for i = 1:5
%         scale = 0.5 * i;
%         max = vision.LocalMaximaFinder;
%         max.MaximumNumLocalMaxima = 4;
%         max.NeighborhoodSize = [64*scale+1 64*scale+1];
%         y = patchscore{k}(:);
%         max.Threshold = mean(y) + std(y);
%         faceSpot{i} = step(max, y) / scale;
%     end
% 
%     imagesc(testimage{numOfImage});
    
end
% imagesc(int8(A .* (patchscore > prctile(patchscore(:), 80))));


% for j = 1 : 5
%     facePosition = faceSpot{j};
%     [nrow, ncol] = size(facePosition);
%     for k = 1 : nrow
%         rectangle('Position', [facePosition(k, 1) facePosition(k, 2) 64 64]);
%     end
% end

