import React from 'react';
import { motion } from 'framer-motion';

const ProgressCircle = ({ label, percentage, icon }) => {
  const circumference = 2 * Math.PI * 45;
  const offset = circumference - (percentage / 100) * circumference;

  return (
    <motion.div
      whileHover={{ scale: 1.05 }}
      className="bg-gradient-to-br from-slate-800 to-slate-900 border border-cyan-400/30 rounded-xl p-6 flex flex-col items-center justify-center hover:shadow-lg hover:shadow-cyan-500/50 transition-all"
    >
      <div className="relative w-32 h-32 mb-4">
        <svg className="w-full h-full transform -rotate-90" viewBox="0 0 120 120">
          {/* Background circle */}
          <circle
            cx="60"
            cy="60"
            r="45"
            fill="none"
            stroke="rgba(71, 85, 105, 0.3)"
            strokeWidth="8"
          />
          {/* Progress circle */}
          <motion.circle
            cx="60"
            cy="60"
            r="45"
            fill="none"
            stroke="url(#gradientCircle)"
            strokeWidth="8"
            strokeDasharray={circumference}
            strokeDashoffset={circumference}
            strokeLinecap="round"
            animate={{ strokeDashoffset: offset }}
            transition={{ duration: 1.5, ease: 'easeOut' }}
          />
          <defs>
            <linearGradient id="gradientCircle" x1="0%" y1="0%" x2="100%" y2="100%">
              <stop offset="0%" stopColor="#06b6d4" />
              <stop offset="100%" stopColor="#3b82f6" />
            </linearGradient>
          </defs>
        </svg>
        {/* Center content */}
        <div className="absolute inset-0 flex flex-col items-center justify-center">
          <span className="text-2xl">{icon}</span>
          <span className="text-xl font-bold text-cyan-400">{percentage}%</span>
        </div>
      </div>
      <p className="text-gray-300 font-medium text-center">{label}</p>
    </motion.div>
  );
};

export default ProgressCircle;
