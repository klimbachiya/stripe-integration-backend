# Stripe Integration Backend

This is the backend service for Stripe checkout integration, built using **Spring Boot**. It handles payments and communicates with the Stripe API securely.

## Table of Contents

- [Technologies Used](#technologies-used)
- [Features](#features)
- [Setup](#setup)
    - [Run Locally](#run-locally)
    - [Environment Variables](#environment-variables)
- [API Endpoints](#api-endpoints)
- [Security Measures](#security-measures)
- [Deployment](#deployment)
    - [Deploying to Render](#deploying-to-render)
- [License](#license)

## Technologies Used

- **Java 17+**
- **Spring Boot**
- **Stripe API**
- **Maven**

## Features

- Secure Stripe checkout session creation
- CORS protection allowing only the frontend to access APIs
- Environment-based secret management for Stripe API keys

## Setup

### Run Locally

1. **Clone the Repository:**

   ```bash
   git clone https://github.com/klimbachiya/stripe-integration-backend.git
   cd stripe-integration-backend

2. **Install Dependencies:**

   ```bash
   mvn clean install

3. **Set Up Environment Variables:**

   ```bash
    $env:STRIPE_SECRET_KEY="your_secret_key_here"
    mvn spring-boot:run

4. **Run the Application:**

    ```bash
   mvn spring-boot:run

The backend will now be running on http://localhost:8080.

### Environment Variables

Make sure to set the following environment variable for your local development:

- **STRIPE_SECRET_KEY: Your secret Stripe API key. You can get this from your Stripe account under the API keys section.**

## API Endpoints

Create Stripe Checkout Session
- **Endpoint: /api/checkout**
- **Method: POST**
- **Request Body:**

    ```bash
    {
  "productId": "12345",
  "quantity": 1
    }

- **Response:**

    ```bash
    {
  "sessionId": "cs_test_123456789"
    }

## Security Measures
- **CORS is restricted to allow only requests from your frontend URL.**
- **Stripe API key is hidden using environment variables or GitHub Secrets for security.**

## Deployment

### Deploying to Render

To deploy the backend to Render using a Dockerfile, follow these steps:

1. **Add Environment Variables in Render:**

Go to your Render dashboard and select your backend service. In the Environment section, add the following environment variable:
- **Key: STRIPE_SECRET_KEY**
- **Value: Your Stripe secret key (e.g., sk_test_XXXXXXXXXXXXXXXXXXXXXXXX)**

2. **Deploy the Application:**

After configuring your environment variables, push the code to your GitHub repository. Render will automatically detect your Dockerfile and deploy the application.

3. **Access the Application:**

After deployment, Render will give you a URL for your backend API. You can use this URL in your frontend to make API requests.

## License
This project is open-source and available under the MIT License.


