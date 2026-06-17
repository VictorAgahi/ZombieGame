#!/bin/bash

# Configuration script for .env file

if [ -f .env ]; then
    echo ".env file already exists. Do you want to overwrite it? (y/n)"
    read -r response
    if [ "$response" != "y" ]; then
        echo "Setup cancelled."
        exit 0
    fi
fi

# Generate a random 256-bit key and encode it in Base64
# We use openssl to generate 32 bytes (256 bits) of random data
GENERATED_SECRET=$(openssl rand -base64 32)

if [ -f .env.example ]; then
    cp .env.example .env
    # Replace the placeholder or default secret with the generated one
    # Using sed to find the line starting with JWT_SECRET_KEY
    if [[ "$OSTYPE" == "darwin"* ]]; then
        # macOS sed needs an empty string for the -i flag
        sed -i "" "s|^JWT_SECRET_KEY=.*|JWT_SECRET_KEY=$GENERATED_SECRET|" .env
    else
        sed -i "s|^JWT_SECRET_KEY=.*|JWT_SECRET_KEY=$GENERATED_SECRET|" .env
    fi
    echo ".env file created with a fresh JWT_SECRET_KEY."
else
    echo "Error: .env.example not found. Creating a basic .env file."
    echo "JWT_SECRET_KEY=$GENERATED_SECRET" > .env
    echo "JWT_EXPIRATION=86400000" >> .env
fi

echo "Setup complete. You can now run the application."
