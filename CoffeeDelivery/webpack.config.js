const path = require('path');

module.exports = {
    entry: {
        catalog: './src/main/resources/static/catalogEntryPoint.js',
        home: './src/main/resources/static/homeEntryPoint.js',
        basket: './src/main/resources/static/basketEntryPoint.js'
    },
    output: {
        path: path.resolve(__dirname, 'src/main/resources/static/js'),
        filename: '[name].bundle.js'
    },
    module: {
        rules: [
            {
                test: /\.(js|jsx)$/,
                exclude: /node_modules/,
                use: {
                    loader: 'babel-loader',
                    options: {
                        presets: ['@babel/preset-env', '@babel/preset-react']
                    }
                },
            },
            {
                test: /\.(png|jpe?g|gif|svg)$/i,
                use: [
                    {
                        loader: 'file-loader',
                        options: {
                            name: '[path][name].[ext]',
                            context: 'src/main/resources/static/src/public',
                            outputPath: 'images',
                            publicPath: '/src/public/',
                            useRelativePaths: true,
                        },
                    },
                ],
            }
        ]
    },
    resolve: {
        extensions: ['.js', '.jsx']
    },
    mode: 'development'
};