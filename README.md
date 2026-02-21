# Spring AI MCP Demo Project

This project demonstrates the **Model Context Protocol (MCP)** using **Spring AI**. It showcases how a server can expose specialized tools and resources, and how a client can leverage an LLM (Large Language Model) to interact with these capabilities via natural language.

## Project Architecture

The project is structured as a Maven multi-module project consisting of a server and a client.

- **[mcp-server](mcp-server)**:
    - **Purpose**: Acts as the MCP Server, providing tools and resources to external clients.
    - **Key Features**:
        - Uses the `@McpTool` annotation to register tools that can be called by LLMs.
        - Currently implements a `getWeather(city)` tool.
        - Configured to use the **SSE (Server-Sent Events)** protocol for communication.
    - **Endpoint**: Defaults to exposing the MCP stream at `/mcp`.

- **[mcp-client](mcp-client)**:
    - **Purpose**: Acts as the MCP Client, connecting to the server and using an LLM to process requests.
    - **Key Features**:
        - Connects to the server's SSE endpoint.
        - Uses **Spring AI's ChatClient** to integrate with AI models (configured for OpenAI/Gemini).
        - Automatically discovers and utilizes tools exposed by the `mcp-server`.

---

## Getting Started

### Prerequisites
- JDK 21+
- Maven 3.9+
- An API Key for OpenAI or Google Gemini.

### Running the Project

1. **Start the Server**:
   ```bash
   cd mcp-server
   mvn spring-boot:run
   ```
   The server will start on `http://localhost:8080`.

2. **Start the Client**:
   ```bash
   cd mcp-client
   mvn spring-boot:run
   ```
   The client will connect to the server and execute a sample prompt (e.g., "What is the weather in San Francisco?").

---

## Testing with Postman (MCP Feature)

Postman now natively supports the Model Context Protocol, allowing you to debug and test MCP servers directly.

### Steps to Test:

1. **Start the `mcp-server`** as described above.
2. **Open Postman** (v11.23+ recommended).
3. **Create a New Request**: Click **New** -> **MCP Request**.
4. **Configure the Connection**:
    - Select **SSE** as the transport type.
    - **Primary Endpoint**: Set the URL to `http://localhost:8080/mcp`.
    - **Alternative Endpoint**: If you encounter connection issues, try `http://localhost:8080/sse` (this path is sometimes used depending on specific SSE configurations).
5. **Connect**: Click the **Connect** button.
6. **Explore Tools**: 
    - Once connected, you can see the available tools listed (e.g., `getWeather`).
    - Select `getWeather`, provide a `city` argument (e.g., `London`), and click **Execute**.
7. **View Logs**: Use the **Timeline** tab in Postman to inspect the JSON-RPC messages exchanged between Postman and your server.
