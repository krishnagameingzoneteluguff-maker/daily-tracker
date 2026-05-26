import React from 'react';
import { Link } from 'react-router-dom';
import { motion } from 'framer-motion';

const NotFound = () => {
  return (
    <div className="min-h-screen bg-gradient-to-br from-slate-900 via-blue-900 to-black flex items-center justify-center p-6">
      <motion.div
        initial={{ opacity: 0, y: 20 }}
        animate={{ opacity: 1, y: 0 }}
        className="text-center"
      >
        <h1 className="text-8xl font-bold mb-4 bg-gradient-to-r from-cyan-400 to-blue-400 bg-clip-text text-transparent">
          404
        </h1>
        <p className="text-3xl font-bold text-white mb-4">Page Not Found</p>
        <p className="text-gray-400 mb-8 text-lg">The page you're looking for doesn't exist.</p>
        <Link
          to="/"
          className="inline-block bg-gradient-to-r from-cyan-500 to-blue-500 px-8 py-3 rounded-lg font-semibold text-white hover:shadow-lg hover:shadow-cyan-500/50 transition-all"
        >
          🏠 Go Home
        </Link>
      </motion.div>
    </div>
  );
};

export default NotFound;