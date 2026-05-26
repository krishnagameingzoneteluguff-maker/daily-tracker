import React from 'react';
import { motion } from 'framer-motion';

const StatCard = ({ title, value, icon, color = 'cyan', progress }) => {
  const colorClasses = {
    cyan: 'from-cyan-500 to-blue-500 shadow-cyan-500/50',
    orange: 'from-orange-500 to-red-500 shadow-orange-500/50',
    purple: 'from-purple-500 to-pink-500 shadow-purple-500/50',
    yellow: 'from-yellow-500 to-orange-500 shadow-yellow-500/50',
  };

  return (
    <motion.div
      whileHover={{ scale: 1.05, y: -5 }}
      className={`bg-gradient-to-br from-slate-800 to-slate-900 border border-${color}-400/30 rounded-xl p-6 hover:shadow-lg hover:shadow-${color}-500/50 transition-all`}
    >
      <div className="flex justify-between items-start mb-4">
        <div>
          <p className="text-gray-400 text-sm font-medium">{title}</p>
          <h3 className={`text-3xl font-bold bg-gradient-to-r ${colorClasses[color].split(' ')[0]} ${colorClasses[color].split(' ')[1]} bg-clip-text text-transparent mt-1`}>
            {value}
          </h3>
        </div>
        <span className="text-3xl">{icon}</span>
      </div>
      
      {progress !== undefined && (
        <div className="w-full bg-slate-700 rounded-full h-2 mt-4 overflow-hidden">
          <motion.div
            className={`h-full bg-gradient-to-r ${colorClasses[color].split(' ')[0]} ${colorClasses[color].split(' ')[1]}`}
            initial={{ width: 0 }}
            animate={{ width: `${progress}%` }}
            transition={{ duration: 1, ease: 'easeOut' }}
          />
        </div>
      )}
    </motion.div>
  );
};

export default StatCard;
