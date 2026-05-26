import React, { useState } from 'react';
import { motion } from 'framer-motion';

const VayuAI = () => {
  const [messages, setMessages] = useState([
    { type: 'bot', text: 'Hello! I\'m Vayu AI, your personal assistant. How can I help you today? 🤖' }
  ]);
  const [input, setInput] = useState('');

  const handleSend = () => {
    if (input.trim()) {
      setMessages([...messages, { type: 'user', text: input }]);
      setTimeout(() => {
        setMessages(prev => [...prev, {
          type: 'bot',
          text: 'I\'m analyzing your message and generating a response... This is a demo! 🚀'
        }]);
      }, 500);
      setInput('');
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-slate-900 via-blue-900 to-black p-6 pt-24">
      <div className="max-w-2xl mx-auto h-[calc(100vh-120px)] flex flex-col">
        <motion.h1
          className="text-4xl font-bold mb-6 bg-gradient-to-r from-cyan-400 to-blue-400 bg-clip-text text-transparent"
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
        >
          🤖 Vayu AI Assistant
        </motion.h1>

        <div className="flex-1 bg-gradient-to-br from-slate-800 to-slate-900 border border-cyan-400/30 rounded-xl p-6 overflow-y-auto mb-4">
          {messages.map((msg, idx) => (
            <motion.div
              key={idx}
              initial={{ opacity: 0, y: 10 }}
              animate={{ opacity: 1, y: 0 }}
              className={`mb-4 flex ${msg.type === 'user' ? 'justify-end' : 'justify-start'}`}
            >
              <div
                className={`max-w-xs rounded-lg p-3 ${
                  msg.type === 'user'
                    ? 'bg-cyan-500/30 text-cyan-100'
                    : 'bg-slate-700/50 text-gray-200'
                }`}
              >
                {msg.text}
              </div>
            </motion.div>
          ))}
        </div>

        <div className="flex gap-2">
          <input
            type="text"
            value={input}
            onChange={(e) => setInput(e.target.value)}
            onKeyPress={(e) => e.key === 'Enter' && handleSend()}
            placeholder="Ask me anything..."
            className="flex-1 bg-slate-800 border border-cyan-400/30 rounded-lg px-4 py-3 text-white placeholder-gray-500 focus:outline-none focus:border-cyan-400/60"
          />
          <motion.button
            whileHover={{ scale: 1.05 }}
            whileTap={{ scale: 0.95 }}
            onClick={handleSend}
            className="bg-gradient-to-r from-cyan-500 to-blue-500 px-6 py-3 rounded-lg font-semibold text-white hover:shadow-lg hover:shadow-cyan-500/50"
          >
            Send
          </motion.button>
        </div>
      </div>
    </div>
  );
};

export default VayuAI;